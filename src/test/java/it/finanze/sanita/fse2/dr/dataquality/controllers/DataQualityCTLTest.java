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
package it.finanze.sanita.fse2.dr.dataquality.controllers;

import it.finanze.sanita.fse2.dr.dataquality.controller.impl.ValidateCTL;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.impl.GraphSRV;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.finanze.sanita.fse2.dr.dataquality.controllers.base.MockRequests.livenessReq;
import static it.finanze.sanita.fse2.dr.dataquality.config.Constants.Profile.TEST;
import static it.finanze.sanita.fse2.dr.dataquality.utility.FileUtility.getFileFromInternalResources;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.actuate.health.Status.UP;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(TEST)
class DataQualityCTLTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ValidateCTL controller;

	@MockBean
	private GraphSRV service;

	@Test
	void livenessCheckCtlTest() throws Exception {
		mvc.perform(livenessReq()).andExpectAll(
			status().isOk(),
			jsonPath("$.status").value(UP.getCode())
		);
	}

	@Test
	void qualityTest() {
		String bundle = new String(getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), UTF_8);
		FhirOperationDTO fhirOperationDTO = new FhirOperationDTO();
		fhirOperationDTO.setJsonString(bundle);
		// Mock
		when(service.traverseGraph(anyString())).thenReturn(new ArrayList<>());
		// Perform validateBundle
		ValidationResultDTO response = controller.validateBundle(fhirOperationDTO);
		// Assertions
		assertAll(
			() -> assertTrue(response.isValid()),
			() -> assertEquals("The JSON bundle has been validated", response.getMessage())
		);
	}

	@Test
	void qualityErrorTest() {
		FhirOperationDTO fhirOperationDTO = new FhirOperationDTO();
		fhirOperationDTO.setJsonString("error bundle");
		// Mock
		List<String> traverseList = Collections.singletonList("test");
		when(service.traverseGraph(anyString())).thenReturn(traverseList);
		// Perform validateBundle
		ValidationResultDTO response = controller.validateBundle(fhirOperationDTO);
		// Assertions
		assertAll(
			() -> assertFalse(response.isValid()),
			() -> assertNotEquals("Unable to validate JSON bundle due to untraversable bundle resources: [test]", response.getMessage())
		);
	}
}