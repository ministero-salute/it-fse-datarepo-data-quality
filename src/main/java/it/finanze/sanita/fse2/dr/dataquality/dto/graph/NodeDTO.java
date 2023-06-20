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

import it.finanze.sanita.fse2.dr.dataquality.utility.FhirResourceUtility;
import lombok.Data;

import java.util.Objects;

import org.hl7.fhir.instance.model.api.IBaseResource;

@Data
public class NodeDTO implements IGraphResourceDTO {
	private IBaseResource resource;
	private String id;
	private String type;
	private boolean traversed;

	public NodeDTO(ReferenceDTO reference) {
		this.resource = reference.getTarget();
		this.id = reference.getTargetReference();
		this.type = resource != null ? resource.getIdElement().getResourceType() : null;
	}

	public NodeDTO(IBaseResource resource) {
		this.resource = resource;
		this.id = FhirResourceUtility.getResourceAsString(resource);
		this.type = resource != null ? resource.getIdElement().getResourceType() : null;
	}

	@Override
	public String toString() {
		return getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof NodeDTO)) return false;
		NodeDTO node = (NodeDTO) o;
		if (node.getId() == null && this.getId() != null) return false;
		if (node.getId() != null && this.getId() == null) return false;
		if (this.getId() == null) return false;
		return node.getId().equals(this.getId());
	}

}