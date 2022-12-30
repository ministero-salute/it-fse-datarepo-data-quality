/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import it.finanze.sanita.fse2.dr.dataquality.helper.FHIRR4Helper;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
@Slf4j
class ValidationTest {

	@Autowired
	private IValidationSRV validationSRV;

	@Test
	void validationTest() {
		FhirContext context = FhirContext.forR4();
		context.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());
		String bundle = new String(FileUtility.getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), StandardCharsets.UTF_8);
		final Bundle bundleDes = FHIRR4Helper.deserializeResource(Bundle.class, bundle, true);
		log.info(FHIRR4Helper.serializeResource(bundleDes, true, false, false));
		ValidationResultDTO validationResult = validationSRV.validateBundleNormativeR4(bundle);
		assertTrue(validationResult.isValid());
	}
}
