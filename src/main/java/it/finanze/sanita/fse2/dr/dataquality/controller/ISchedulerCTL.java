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

import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_REFRESH_SCHEDULER;
import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_STATUS_PARAMS;

@Tag(name = "Scheduler")
public interface ISchedulerCTL {

    @Operation(summary = "Aggiorna referenze search-params", description = "Aggiorna le referenze dei search-params esposte dal FHIR")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Richiesta update in coda", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RunSchedulerDTO.class))),
        @ApiResponse(responseCode = "423", description = "Richiesta update rigettata", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping(API_REFRESH_SCHEDULER)
    RunSchedulerDTO refresh();

    @Operation(summary = "Recupera le search-params correnti", description = "Recupera le referenze attualmente in uso sul server")
    @ApiResponse(responseCode = "200", description = "Search params correnti", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SearchParamsResponseDTO.class)))
    @GetMapping(API_STATUS_PARAMS)
    SearchParamsResponseDTO status();
}
