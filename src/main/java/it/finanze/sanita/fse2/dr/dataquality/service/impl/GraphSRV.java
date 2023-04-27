package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.*;
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
	private ISearchParamVerifierSRV searchParamVerifierSRV;

	public List<String> traverseGraph(String jsonBundle) {
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
		boolean searchParam = searchParamVerifierSRV.isSearchParam(edge.getSource().getType(), edge.getPath());
		edge.setSearchParam(searchParam);
	}

}
