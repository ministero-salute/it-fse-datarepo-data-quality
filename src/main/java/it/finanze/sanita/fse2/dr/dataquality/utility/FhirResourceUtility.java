package it.finanze.sanita.fse2.dr.dataquality.utility;

import org.hl7.fhir.instance.model.api.IBaseReference;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

public class FhirResourceUtility {

	public static String getResourceAsString(BundleEntryComponent entry) {
		if (entry == null) return null;
		return getResourceAsString(entry.getResource());
	}

	public static String getResourceAsString(IBaseReference reference) {
		if (reference == null) return null;
		return getResourceAsString(reference.getResource());
	}

	public static String getResourceAsString(IBaseResource resource) {
		if (resource == null) return null;
		return resource.getIdElement().getResourceType() + "/" + resource.getIdElement().getIdPart();
	}
	
//
//	public static List<String> getAllResourcesList(String jsonBundle) {
//		return getAllResources(jsonBundle)
//				.values()
//				.stream()
//				.collect(Collectors.toList());
//	}
//	
//	public static Map<String, String> getAllResources(String jsonBundle) {
//		Map<String, String> allValues = FlattenJsonUtility.flattenJson(jsonBundle);
//		return allValues
//				.entrySet()
//				.stream()
//				.filter(entry -> isResource(entry))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//	}
//
//	private static boolean isResource(Entry<String, String> entry) {
//		return entry.getKey().contains("reference");
//	}
	
}
