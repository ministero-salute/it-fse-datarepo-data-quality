/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.helper.FHIRR4Helper;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationSRV implements IValidationSRV {

	 
	@Override
	public ValidationResultDTO validateBundleNormativeR4(final String bundle) {
		ValidationResultDTO output = new ValidationResultDTO();
		boolean esitoValidazione = false;
		StringBuilder sb = new StringBuilder();
		try {
			FhirContext context =  FHIRR4Helper.getContext();
			ValidationSupportChain validationSupportChain = new ValidationSupportChain(
					new DefaultProfileValidationSupport(context),
					new InMemoryTerminologyServerValidationSupport(context),
					new CommonCodeSystemsTerminologyService(context));

			FhirValidator validator = context.newValidator();
			IValidatorModule module = new FhirInstanceValidator(validationSupportChain);
			validator.registerValidatorModule(module);

			ValidationResult result = validator.validateWithResult(bundle);
			for(SingleValidationMessage msg : result.getMessages()) {
				if(ResultSeverityEnum.ERROR.equals(msg.getSeverity()) || ResultSeverityEnum.FATAL.equals(msg.getSeverity())) {
					sb.append(String.format("Severity: %s Message: %s", msg.getSeverity().getCode(),msg.getMessage()));
				}
			}

			if(StringUtils.isEmpty(sb.toString())) {
				esitoValidazione = true;
			}

			output.setMessage(sb.toString());
			output.setValid(esitoValidazione);

		} catch(Exception ex) {
			log.error("Error while perform validate bundle normative R4 : " , ex);
			output.setMessage(ex.getMessage());
		}
		return output;
	}
}
