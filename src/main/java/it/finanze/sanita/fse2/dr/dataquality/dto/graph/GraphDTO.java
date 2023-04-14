package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

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
	
	public List<NodeDTO> getNodes() {
		if (nodes == null) nodes = new ArrayList<>();
		return nodes;
	}

	public List<EdgeDTO> getEdges() {
		if (edges == null) edges = new ArrayList<>();
		return edges;
	}

	public List<String> getNotTraversedResources() {
		return getNodes()
				.stream()
				.filter(node -> !node.isTraversed())
				.map(NodeDTO::getId)
				.collect(Collectors.toList());
	}

	public NodeDTO getDocumentReferenceNode() {
		return getNodes()
				.stream()
				.filter(node -> node.getId().contains("DocumentReference"))
				.findFirst()
				.orElse(null);
	}

}