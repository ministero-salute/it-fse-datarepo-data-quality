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