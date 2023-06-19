/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GraphDTO {

	private static final String START_NODE = "DocumentReference";

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
				.filter(edge -> edge.getSource().equals(source))
				.collect(Collectors.toList());
	}

	public EdgeDTO getFirstNotTraversedEdge(NodeDTO source, NodeDTO target, String path) {
		return getEdges(source, target, path)
				.stream()
				.filter(EdgeDTO::isNotTraversed)
				.findFirst()
				.orElse(null);
	}

	public List<EdgeDTO> getEdges(NodeDTO source, NodeDTO target, String path) {
		return getEdges()
				.stream()
				.filter(edge -> edge.hasSource(source))
				.filter(edge -> edge.hasTarget(target))
				.filter(edge -> edge.getPath().equals(path))
				.collect(Collectors.toList());
	}

	public List<NodeDTO> getNodes() {
		if (nodes == null) nodes = new ArrayList<>();
		return nodes.stream().filter(node -> node.getId() != null).collect(Collectors.toList());
	}

	public List<EdgeDTO> getEdges() {
		if (edges == null) edges = new ArrayList<>();
		return edges;
	}

	public List<IGraphResourceDTO> getNotTraversedResources() {
		List<EdgeDTO> edges = getNotTraversedEdges();
		List<NodeDTO> nodes = getNotTraversedNodes();
		nodes.removeIf(node -> hasNode(edges, node));
		List<IGraphResourceDTO> resources = new ArrayList<>(edges);
		resources.addAll(nodes);
		return resources;
	}

	public NodeDTO getStartNode() {
		return getNodes()
				.stream()
				.filter(node -> node.getId().contains(START_NODE))
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
		EdgeDTO edgeDTO = getFirstNotTraversedEdge(edge.getSource(), edge.getTarget(), edge.getPath());
		if (edgeDTO == null) return;
		edgeDTO.setTraversed(true);
	}

	private List<NodeDTO> getNotTraversedNodes() {
		return getNodes()
				.stream()
				.filter(node -> !node.isTraversed())
				.collect(Collectors.toList());
	}

	private List<EdgeDTO> getNotTraversedEdges() {
		return getEdges()
				.stream()
				.filter(edge -> !edge.isTraversed())
				.collect(Collectors.toList());
	}

	private boolean hasNode(List<EdgeDTO> edges, NodeDTO node) {
		return edges
				.stream()
				.anyMatch(edge -> edge.hasSource(node) || edge.hasTarget(node));
	}

}