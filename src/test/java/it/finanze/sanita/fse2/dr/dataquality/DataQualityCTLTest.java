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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;
import it.finanze.sanita.fse2.dr.dataquality.utility.FileUtility;
import it.finanze.sanita.fse2.dr.dataquality.utility.JsonUtility;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.controller.impl.ValidateCTL;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ComponentScan
@ExtendWith(SpringExtension.class)
@ActiveProfiles(Constants.Profile.TEST)
@DisplayName("DataQuality Controller Unit Test")
class DataQualityCTLTest {

	@Autowired
	HttpServletRequest request;

	@Autowired
	ServletWebServerApplicationContext webServerAppCtxt;

	@Autowired
	MockMvc mvc;

	@Autowired
	ValidateCTL controllerDataQuality;

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
		MockHttpServletResponse response = mvc.perform(post("/v1/validate-bundle")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new Gson().toJson(fhirOperationDTO)))
				.andExpect(status().isOk())
				.andReturn().getResponse();

		ValidationResultDTO contentResponse = JsonUtility.jsonToObject(new String(response.getContentAsByteArray()), ValidationResultDTO.class);

		assertAll(
				() -> assertTrue(contentResponse.isValid()),
				() -> assertEquals("", contentResponse.getMessage())
		);
	}

	@Test
	void qualityErrorTest() throws Exception {
		FhirOperationDTO fhirOperationDTO = new FhirOperationDTO();
		fhirOperationDTO.setJsonString("error bundle");
		MockHttpServletResponse response = mvc.perform(post("/v1/validate-bundle")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new Gson().toJson(fhirOperationDTO)))
				.andExpect(status().isOk())
				.andReturn().getResponse();

		ValidationResultDTO contentResponse = JsonUtility.jsonToObject(new String(response.getContentAsByteArray()), ValidationResultDTO.class);

		assertAll(
				() -> assertFalse(contentResponse.isValid()),
				() -> assertNotEquals("", contentResponse.getMessage())
		);
	}
}