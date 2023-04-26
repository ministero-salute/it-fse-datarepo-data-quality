/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;
import it.finanze.sanita.fse2.dr.dataquality.config.BundleCFG;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.EdgeDTO;
import it.finanze.sanita.fse2.dr.dataquality.helper.FHIRR4Helper;
import it.finanze.sanita.fse2.dr.dataquality.service.IGraphSRV;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ValidationSRV implements IValidationSRV {

	@Autowired
	private BundleCFG bundleCFG;

	@Autowired
	private IGraphSRV graphSRV;
	
	@Override
	public ValidationResultDTO validateBundle(String jsonBundle) {		
		ValidationResultDTO result = new ValidationResultDTO();
		result.getNormativeR4Messages().addAll(Arrays.asList("zozzone"));//validateNormativeR4(jsonBundle));
		//result.getNotTraversedResources().addAll(traverseGraph(jsonBundle));
		return result;
	}
	
	private List<EdgeDTO> traverseGraph(String jsonBundle) {
		if(bundleCFG.isTraverseResources()) return graphSRV.traverseGraph(jsonBundle);
		log.debug("Skipping traversing bundle resources");
		return new ArrayList<>();
	}

	private List<String> validateNormativeR4(final String bundle) {
		try {
			ValidationResult result = getValidator().validateWithResult(bundle);
			return getMessages(result);
		} catch(Exception ex) {
			log.error("Error while perform validate bundle normative R4 : " , ex);
			return Arrays.asList(ex.getMessage());
		}
	}

	private List<String> getMessages(ValidationResult result) {
		List<ResultSeverityEnum> errors = Arrays.asList(ResultSeverityEnum.ERROR, ResultSeverityEnum.FATAL);
		return result
				.getMessages()
				.stream()
				.filter(msg -> errors.contains(msg.getSeverity()))
				.map(msg -> String.format("Severity: %s Message: %s", msg.getSeverity().getCode(),msg.getMessage()))
				.collect(Collectors.toList());
	}
	
	private FhirValidator getValidator() {
		FhirContext context =  FHIRR4Helper.getContext();
		ValidationSupportChain validationSupportChain = new ValidationSupportChain(
				new DefaultProfileValidationSupport(context),
				new InMemoryTerminologyServerValidationSupport(context),
				new CommonCodeSystemsTerminologyService(context));

		FhirValidator validator = context.newValidator();
		IValidatorModule module = new FhirInstanceValidator(validationSupportChain);
		validator.registerValidatorModule(module);
		return validator;
	}

//	private List<String> validateHttpMethods(Bundle bundle) {
//		return bundle
//				.getEntry()
//				.stream()
//				.filter(entry -> hasPutMethod(entry))
//				.filter(entry -> StringUtils.isBlank(entry.getRequest().getUrl()))
//				.map(entry -> entry.getFullUrl())
//				.collect(Collectors.toList());
//	}
//
//	private boolean hasPutMethod(BundleEntryComponent entry) {
//		if (entry.getRequest() == null) return false;
//		if (entry.getRequest().getMethod() == null) return false;
//		return entry.getRequest().getMethod() == HTTPVerb.PUT;
//	}

	
 }