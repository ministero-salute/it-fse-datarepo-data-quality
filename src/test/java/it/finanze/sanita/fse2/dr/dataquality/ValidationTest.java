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
package it.finanze.sanita.fse2.dr.dataquality;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.graph.IGraphResourceDTO;
import it.finanze.sanita.fse2.dr.dataquality.helper.FHIRR4Helper;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import it.finanze.sanita.fse2.dr.dataquality.service.impl.GraphSRV;
import it.finanze.sanita.fse2.dr.dataquality.utility.FileUtility;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
@Slf4j
class ValidationTest {

	@Autowired
	private IValidationSRV validationSRV;

	@MockBean
	private GraphSRV graphSRV;

	@Test
	void validationTest() throws Exception {
		FhirContext context = FhirContext.forR4();
		context.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());
		String bundle = new String(FileUtility.getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), StandardCharsets.UTF_8);
		final Bundle bundleDes = FHIRR4Helper.deserializeResource(Bundle.class, bundle, true);
		log.info(FHIRR4Helper.serializeResource(bundleDes, true, false, false));
		// Mock traverse flow
		when(graphSRV.traverseGraph(bundle)).thenReturn(new ArrayList<String>());
		// Perform validateBundle
		ValidationResultDTO validationResult = validationSRV.validateBundle(bundle);
		assertTrue(validationResult.isValid());
	}
}
