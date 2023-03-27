package it.finanze.sanita.fse2.dr.dataquality.utility;

import java.util.List;
import java.util.stream.Collectors;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;

public class DepthFirstSearchUtility {

	public static void traverse(GraphDTO graph) {
		NodeDTO start = graph.getDocumentReferenceNode();
		applyDFS(start, graph);
	}
	
	private static void applyDFS(NodeDTO start, GraphDTO graph) {
	    NodeDTO currentNode = graph.getNode(start.getId());
	    currentNode.setTraversed(true);
		List<NodeDTO> nodesToVisit = getNodesToVisit(currentNode, graph);
	    nodesToVisit.forEach(node -> applyDFS(node, graph));
	}

	private static List<NodeDTO> getNodesToVisit(NodeDTO currentNode, GraphDTO graph) {
		return graph
				.getEdgesWithSource(currentNode)
				.stream()
				.map(edge -> graph.getNode(edge.getTarget().getId()))
				.filter(node -> !node.isTraversed())
				.collect(Collectors.toList());
	}

}
