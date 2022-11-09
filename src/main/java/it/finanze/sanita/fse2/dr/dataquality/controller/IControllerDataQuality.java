/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.controller;

import javax.servlet.http.HttpServletRequest;

import it.finanze.sanita.fse2.dr.dataquality.dto.response.FhirNormalizedDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;

@RequestMapping(path = "/v1")
@Tag(name = "Servizio operazioni normalizzazione")
public interface IControllerDataQuality {

	@Operation(summary = "Get mapping", description = "Get mapping for Data quality")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operazione eseguita correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))) })
	@GetMapping("/getMapping")
	Object getDataQuality(HttpServletRequest httpServletRequest);

	@Operation(summary = "Normalize operation", description = "Normalize jsonString using FHIR server")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operazione eseguita correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))) })
	@PostMapping("/normalize")
	ResponseEntity<FhirNormalizedDTO> normalize(@RequestBody FhirOperationDTO requestBody,
												HttpServletRequest request);

	@Operation(summary = "Delete mapping", description = "Post mapping for Data quality")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operazione eseguita correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))) })
	@DeleteMapping("/deleteMapping")
	Object deleteDataQuality(HttpServletRequest httpServletRequest);

	@Operation(summary = "Put mapping", description = "Put mapping for Data quality")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operazione eseguita correttamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Object.class))) })
	@PutMapping("/putMapping")
	Object putDataQuality(HttpServletRequest httpServletRequest);

}
