/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.utility;

import org.hl7.fhir.instance.model.api.IBaseReference;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
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
