/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.utility;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.util.BundleUtil;
import ca.uhn.fhir.util.ResourceReferenceInfo;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.ReferenceDTO;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import java.util.List;
import java.util.stream.Collectors;

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