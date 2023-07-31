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

import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.graph.AbstractGraphTest;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import it.finanze.sanita.fse2.dr.dataquality.service.impl.GraphSRV;
import it.finanze.sanita.fse2.dr.dataquality.service.impl.SearchParamVerifierSRV;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static it.finanze.sanita.fse2.dr.dataquality.config.Constants.Profile.TEST;
import static it.finanze.sanita.fse2.dr.dataquality.utility.FileUtility.getFileFromInternalResources;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(TEST)
class ValidationTest extends AbstractGraphTest {

	@Autowired
	private IValidationSRV validations;

	@SpyBean
	private SearchParamVerifierSRV params;

	@SpyBean
	private GraphSRV graphSRV;

	@Test
	void isNotValid() {
		// Retrieve file
		String bundle = new String(getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), UTF_8);
		// Mock update
		doNothing().when(params).tryToUpdateParamsIfNecessary();
		// Perform validation (without search params)
		ValidationResultDTO validationResult = validations.validateBundle(bundle);
		// Verify
		assertFalse(validationResult.isValid());
	}

	@Test
	void isValidMock() {
		// Retrieve file
		String bundle = new String(getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), UTF_8);
		// Mock update
		doNothing().when(params).tryToUpdateParamsIfNecessary();
		// Mock traverse flow
		doReturn(new ArrayList<>()).when(graphSRV).traverseGraph(anyString());
		// Perform validation
		ValidationResultDTO validationResult = validations.validateBundle(bundle);
		// Verify
		assertTrue(validationResult.isValid());
	}
}
