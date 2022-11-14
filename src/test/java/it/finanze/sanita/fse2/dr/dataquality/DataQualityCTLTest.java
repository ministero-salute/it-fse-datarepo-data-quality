/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.controller.impl.ValidateCTL;
import it.finanze.sanita.fse2.dr.dataquality.dto.response.FhirNormalizedDTO;

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

	void livenessCheckCtlTest() throws Exception {

		mvc.perform(get("http://localhost:" + webServerAppCtxt.getWebServer().getPort() + "/status")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().is2xxSuccessful());
	}

	@Test
	@DisplayName("Response status OK")
	void responseNormalize() throws Exception {

		FhirNormalizedDTO res = new FhirNormalizedDTO();

		res.setJsonString(DOCUMENT_TEST_JSON_STRING_PUT);
		res.setMasterIdentifier(DOCUMENT_TEST_MASTER_IDENTIFIER_C);
		res.setNormalized(true);

		// given().willReturn(res);

		mvc.perform(get("http://localhost:" + webServerAppCtxt.getWebServer().getPort() + "/status")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().is2xxSuccessful())
				.andExpect(status().isOk());

	}
 
}