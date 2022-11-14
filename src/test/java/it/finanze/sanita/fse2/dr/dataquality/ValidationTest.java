package it.finanze.sanita.fse2.dr.dataquality;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import ca.uhn.fhir.parser.IParser;
import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import it.finanze.sanita.fse2.dr.dataquality.utility.FileUtility;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
public class ValidationTest {
	
	@Autowired
	private IValidationSRV validationSRV;
	
	@Test
	void validationTest() {
		FhirContext context = FhirContext.forR4();
		context.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());
		String bundle = new String(FileUtility.getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), StandardCharsets.UTF_8);
        final Bundle bundleDes = deserializeResource(Bundle.class, bundle, false,context);
        System.out.println(serializeResource(bundleDes, true, false, false, context));
		ValidationResultDTO validationResult =  validationSRV.validateBundleNormativeR4(bundle);
		assertTrue(validationResult.isValid());
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T deserializeResource(Class<? extends IBaseResource> resourceClass, String input, Boolean flagJson, FhirContext context) {
		IParser parser = null;
		parser = context.newJsonParser();
		return (T) parser.parseResource(resourceClass, input);
	}
	
	public static String serializeResource(IBaseResource resource, Boolean flagPrettyPrint, Boolean flagSuppressNarratives, Boolean flagSummaryMode,
			FhirContext context) {
		IParser parser = context.newJsonParser();
		parser.setPrettyPrint(flagPrettyPrint);
		parser.setSuppressNarratives(flagSuppressNarratives);
		parser.setSummaryMode(flagSummaryMode);
		return parser.encodeResourceToString(resource);
	}


}
