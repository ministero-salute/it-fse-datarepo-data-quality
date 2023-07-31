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

import lombok.NoArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseReference;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FhirResourceUtility {

	public static String getResourceAsString(BundleEntryComponent entry) {
		if (entry == null) return null;
		return getResourceAsString(entry.getResource());
	}

	public static String getResourceAsString(IBaseReference reference) {
		if (reference == null) return null;
		return getResourceAsString(reference.getResource());
	}

	public static String getReferenceAsString(IBaseReference reference) {
		if (reference == null) return null;
		if (reference.getReferenceElement() == null) return null;
		return getIdTypeAsString(reference.getReferenceElement());
	}

	public static String getResourceAsString(IBaseResource resource) {
		if (resource == null) return null;
		return getIdTypeAsString(resource.getIdElement());
	}

	public static String getIdTypeAsString(IIdType idType) {
		if (idType == null) return null;
		String type = idType.getResourceType();
		String id = idType.getIdPart();
		StringBuilder sb = new StringBuilder();
		if (type != null) sb.append(type);
		if (id != null) sb.append("/").append(id);
		String result = sb.toString();
		return result.isEmpty() ? null : result;
	}
	
}
