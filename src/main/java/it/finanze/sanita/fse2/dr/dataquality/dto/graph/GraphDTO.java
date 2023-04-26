package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GraphDTO {
	private List<NodeDTO> nodes;
	private List<EdgeDTO> edges;
	
	public NodeDTO getNode(String id) {
		return getNodes()
				.stream()
				.filter(node -> node.getId().equals(id))
				.findFirst()
				.orElse(null);
	}
	
	public List<EdgeDTO> getEdgesWithSource(NodeDTO source) {
		return getEdges()
				.stream()
				.filter(edge -> edge.getSource().getId().equals(source.getId()))
				.collect(Collectors.toList());
	}

	public EdgeDTO getEdge(NodeDTO source, NodeDTO target, String path) {
		return getEdges()
				.stream()
				.filter(edge -> edge.getSource().getId().equals(source.getId()))
				.filter(edge -> edge.getTarget().getId().equals(target.getId()))
				.filter(edge -> edge.getPath().equals(path))
				.findFirst()
				.orElse(null);
	}
	
	public List<NodeDTO> getNodes() {
		if (nodes == null) nodes = new ArrayList<>();
		return nodes;
	}

	public List<EdgeDTO> getEdges() {
		if (edges == null) edges = new ArrayList<>();
		return edges;
	}

	public List<EdgeDTO> getNotTraversedResources() {
		return getEdges()
				.stream()
				.filter(edge -> !edge.isTraversed())
				.collect(Collectors.toList());
	}

	public NodeDTO getDocumentReferenceNode() {
		return getNodes()
				.stream()
				.filter(node -> node.getId().contains("DocumentReference"))
				.findFirst()
				.orElse(null);
	}

	public void setNodeTraversed(NodeDTO node) {
		if (node == null) return;
		NodeDTO nodeDTO = getNode(node.getId());
		if (nodeDTO == null) return;
		nodeDTO.setTraversed(true);
	}
    public void setEdgeTraversed(EdgeDTO edge) {
		if (edge == null) return;
		EdgeDTO edgeDTO = getEdge(edge.getSource(), edge.getTarget(), edge.getPath());
		if (edgeDTO == null) return;
		edgeDTO.setTraversed(true);
    }

}