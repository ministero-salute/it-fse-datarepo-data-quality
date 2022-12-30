/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.controller.impl.ValidateCTL;

import java.nio.charset.StandardCharsets;

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
		mvc.perform(get("/status")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().is2xxSuccessful());
	}

	@Test
	void qualityTest() throws Exception {
		String bundle = new String(FileUtility.getFileFromInternalResources("Referto_di_Laboratorio_caso_semplice.json"), StandardCharsets.UTF_8);
		FhirOperationDTO fhirOperationDTO = new FhirOperationDTO();
		fhirOperationDTO.setJsonString(bundle);
		mvc.perform(post("/v1/validate-bundle")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new Gson().toJson(fhirOperationDTO)))
				.andExpect(status().isOk());
	}
 
 
}