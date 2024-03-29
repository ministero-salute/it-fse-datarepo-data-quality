/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.IGraphResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;
import it.finanze.sanita.fse2.dr.dataquality.helper.FHIRR4Helper;
import it.finanze.sanita.fse2.dr.dataquality.service.IGraphSRV;
import it.finanze.sanita.fse2.dr.dataquality.service.ISearchParamVerifierSRV;
import it.finanze.sanita.fse2.dr.dataquality.utility.BundleUtility;
import it.finanze.sanita.fse2.dr.dataquality.utility.DepthFirstSearchUtility;
import org.hl7.fhir.r4.model.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraphSRV implements IGraphSRV {

	@Autowired
	private ISearchParamVerifierSRV service;

	public List<String> traverseGraph(String jsonBundle) {
		// Try to update if search params are empty, otherwise throw for as an illegal state exception
		service.tryToUpdateParamsIfNecessary();
		// Execute
		return traverse(jsonBundle)
				.stream()
				.map(IGraphResourceDTO::toString)
				.collect(Collectors.toList());
	}

	private List<IGraphResourceDTO> traverse(String jsonBundle) {
		Bundle bundle = FHIRR4Helper.deserializeResource(Bundle.class, jsonBundle, true);
		GraphDTO graph = createGraph(bundle);
		DepthFirstSearchUtility.traverse(graph);
		return graph.getNotTraversedResources();
	}

	private GraphDTO createGraph(Bundle bundle) {
		List<NodeDTO> nodes = getNodes(bundle);
		List<EdgeDTO> edges = getEdges(nodes);
		return new GraphDTO(nodes, edges);
	}

	private List<NodeDTO> getNodes(Bundle bundle) {
		return BundleUtility
				.getAllResources(bundle)
				.stream()
				.map(NodeDTO::new)
				.collect(Collectors.toList());
	}

	private List<EdgeDTO> getEdges(List<NodeDTO> nodes) {
		return nodes
				.stream()
				.flatMap(node -> getEdges(node).stream())
				.collect(Collectors.toList());
	}

	private List<EdgeDTO> getEdges(NodeDTO node) {
		return BundleUtility
				.getAllReferences(node.getType(), node.getResource())
				.stream()
				.map(reference -> new EdgeDTO(node, reference))
				.filter(EdgeDTO::isTraversable)
				.peek(this::setSearchParam)
				.collect(Collectors.toList());
	}

	private void setSearchParam(EdgeDTO edge) {
		boolean searchParam = service.isSearchParam(edge.getSource().getType(), edge.getPath());
		edge.setSearchParam(searchParam);
	}

}
