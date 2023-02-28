package it.finanze.sanita.fse2.dr.dataquality.utility;

import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.util.BundleUtil;
import ca.uhn.fhir.util.ResourceReferenceInfo;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.ReferenceDTO;

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
				.filter(ref -> ref.getResourceReference().getResource() != null)
				.map(ref -> getReference(sourceType, ref))
				.collect(Collectors.toList());
	}

	private static ReferenceDTO getReference(String sourceType, ResourceReferenceInfo info) {
		ReferenceDTO reference = new ReferenceDTO();
		reference.setTarget(info.getResourceReference().getResource());
		reference.setPath(sourceType + "." + info.getName());
		return reference;
	}
	
}