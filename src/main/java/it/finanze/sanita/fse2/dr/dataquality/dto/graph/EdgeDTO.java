/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDTO implements IGraphResourceDTO {
	private NodeDTO source;
	private NodeDTO target;
	private String path;
	private boolean traversed;
	private boolean searchParam;

	public EdgeDTO(NodeDTO source, ReferenceDTO reference) {
		this(source, new NodeDTO(reference), reference.getPath(), false, false);
	}

	public boolean isTraversable() {
		return getTarget().getId() != null && !getTarget().getId().isEmpty();
	}

	public boolean isNotTraversed() {
		return !traversed;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (source != null && source.toString() != null) sb.append(source.toString());
		sb.append("^");
		if (target != null && target.toString() != null) sb.append(target.toString());
		sb.append("^");
		if (path != null) sb.append(path);
		return sb.toString();
	}

	public boolean hasSource(NodeDTO source) {
		if (source == null) return false;
		if (getSource() == null) return false;
		return getSource().equals(source);
	}

	public boolean hasTarget(NodeDTO target) {
		if (target == null) return false;
		if (getTarget() == null) return false;
		return getTarget().equals(target);
	}
}