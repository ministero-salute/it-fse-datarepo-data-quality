package it.finanze.sanita.fse2.dr.dataquality.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.tools.RunSchedulerDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Scheduler")
@RequestMapping("/v1")
public interface ISchedulerCTL {

    @Operation(summary = "Aggiorna referenze search-params", description = "Aggiorna le referenze dei search-params esposte dal FHIR")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Richiesta update in coda", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RunSchedulerDTO.class))),
        @ApiResponse(responseCode = "423", description = "Richiesta update rigettata", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/refresh")
    RunSchedulerDTO refresh();

    @Operation(summary = "Recupera le search-params correnti", description = "Recupera le referenze attualmente in uso sul server")
    @ApiResponse(responseCode = "200", description = "Search params correnti", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SearchParamsResponseDTO.class)))
    @GetMapping("/status")
    SearchParamsResponseDTO status();
}
