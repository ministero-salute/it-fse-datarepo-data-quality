package it.finanze.sanita.fse2.dr.dataquality.utility;

import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.GraphDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.NodeDTO;

import java.util.List;
import java.util.stream.Collectors;

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
