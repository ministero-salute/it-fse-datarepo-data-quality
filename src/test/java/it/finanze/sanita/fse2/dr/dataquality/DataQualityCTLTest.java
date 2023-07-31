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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.controller.impl.ValidateCTL;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.impl.GraphSRV;
import it.finanze.sanita.fse2.dr.dataquality.utility.FileUtility;
import it.finanze.sanita.fse2.dr.dataquality.utility.JsonUtility;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ComponentScan
@ActiveProfiles(Constants.Profile.TEST)
@DisplayName("DataQuality Controller Unit Test")
class DataQualityCTLTest {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ValidateCTL controller;

	@MockBean
	private GraphSRV graphSRV;

	static final String DOCUMENT_TEST_JSON_STRING_PUT = "{\"jsonString\": \"testPut\"}";
	static final String DOCUMENT_TEST_MASTER_IDENTIFIER_C = "testMasterIdentifierRepoC";

	@Test
	void livenessCheckCtlTest() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/status")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpectAll(status().isOk()).andReturn().getResponse();


		Map contentResponse = JsonUtility.jsonToObject(new String(response.getContentAsByteArray()), Map.class);
		assertTrue(contentResponse.containsKey("status"));
		assertEquals(Status.UP.getCode(), contentResponse.get("status"));
	}

	@Test
	void qualityTest() throws Exception {
		String bundle = new String(FileUtility.getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), StandardCharsets.UTF_8);
		FhirOperationDTO fhirOperationDTO = new FhirOperationDTO();
		fhirOperationDTO.setJsonString(bundle);
		// Mock
		when(graphSRV.traverseGraph(anyString())).thenReturn(new ArrayList<String>());
		// Perform validateBundle
		ValidationResultDTO contentResponse = controller.validateBundle(fhirOperationDTO, request);
		// Assertions
		assertAll(
				() -> assertTrue(contentResponse.isValid()),
				() -> assertEquals("The JSON bundle has been validated", contentResponse.getMessage())
		);
	}

	@Test
	void qualityErrorTest() throws Exception {
		FhirOperationDTO fhirOperationDTO = new FhirOperationDTO();
		fhirOperationDTO.setJsonString("error bundle");
		// Mock
		List<String> traverseList = new ArrayList<>();
		traverseList.add("test");
		when(graphSRV.traverseGraph(anyString())).thenReturn(traverseList);
		// Perform validateBundle
		ValidationResultDTO contentResponse = controller.validateBundle(fhirOperationDTO, request);
		// Assertions
		assertAll(
				() -> assertFalse(contentResponse.isValid()),
				() -> assertNotEquals("Unable to validate JSON bundle due to untraversable bundle resources: [test]", contentResponse.getMessage())
		);
	}
}