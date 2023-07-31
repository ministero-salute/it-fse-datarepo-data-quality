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

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.util.BundleUtil;
import ca.uhn.fhir.util.ResourceReferenceInfo;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.ReferenceDTO;
import lombok.NoArgsConstructor;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class BundleUtility {

	public static final FhirContext fhirContext = FhirContext.forR4();
	
	public static List<IBaseResource> getAllResources(Bundle bundle) {
		return BundleUtil
				.toListOfResources(fhirContext, bundle)
				.stream()
				.distinct()
				.collect(Collectors.toList());
	}
	
	public static List<ReferenceDTO> getAllReferences(Bundle bundle) {
		return bundle
				.getEntry()
				.stream()
				.flatMap(entry -> getAllReferences(entry).stream())
				.distinct()
				.collect(Collectors.toList());
	}
	
	public static List<ReferenceDTO> getAllReferences(BundleEntryComponent entry) {
		String sourceType = entry.getResource().fhirType();
		return getAllReferences(sourceType, entry.getResource());
	}
	
	public static List<ReferenceDTO> getAllReferences(String sourceType, IBaseResource resource) {
		return fhirContext
				.newTerser()
				.getAllResourceReferences(resource)
				.stream()
				.map(ref -> getReference(sourceType, ref))
				.collect(Collectors.toList());
	}

	private static ReferenceDTO getReference(String sourceType, ResourceReferenceInfo info) {
		ReferenceDTO reference = new ReferenceDTO();
		reference.setTarget(info.getResourceReference().getResource());
		reference.setTargetReference(getReference(info));
		reference.setPath(sourceType + "." + info.getName());
		return reference;
	}

	private static String getReference(ResourceReferenceInfo info) {
		if (info == null) return null;
		return FhirResourceUtility.getReferenceAsString(info.getResourceReference());
	}

}