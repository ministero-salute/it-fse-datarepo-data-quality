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
package it.finanze.sanita.fse2.dr.dataquality.utility;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class DepthFirstSearchUtility {

	public static void traverse(GraphDTO graph) {
		NodeDTO start = graph.getStartNode();
		applyDFS(start, null, graph);
	}

	private static void applyDFS(NodeDTO currentNode, EdgeDTO fromEdge, GraphDTO graph) {
		graph.setEdgeTraversed(fromEdge);
		if (currentNode.isTraversed()) return;
		graph.setNodeTraversed(currentNode);
		List<EdgeDTO> edgesToVisit = getEdgesToVisit(currentNode, graph);
		edgesToVisit.forEach(edge -> applyDFS(edge.getTarget(), edge, graph));
	}

	private static List<EdgeDTO> getEdgesToVisit(NodeDTO currentNode, GraphDTO graph) {
		return graph
				.getEdgesWithSource(currentNode)
				.stream()
				.filter(EdgeDTO::isNotTraversed)
				.filter(EdgeDTO::isSearchParam)
				.collect(Collectors.toList());
	}

}
