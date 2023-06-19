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
package it.finanze.sanita.fse2.dr.dataquality.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import it.finanze.sanita.fse2.dr.dataquality.client.ISrvQueryClient;
import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.config.MicroservicesURLCFG;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * The implementation of the Srv Query Client 
 */
@Slf4j
@Component
public class SrvQueryClient implements ISrvQueryClient {

    /**
     * Rest Template 
     */
    @Autowired
    private RestTemplate rest;

    /*
     * Microservices URL Config 
     */
    @Autowired
    private MicroservicesURLCFG services;

	@Override
	public SearchParamsResponseDTO getSearchParams() throws RuntimeException {
		log.debug("[EDS QUERY] Calling for SearchParameters - START");
		ResponseEntity<SearchParamsResponseDTO> response;
		String url = services.getEdsQueryHost() + "/v1/searchParams";
		response = rest.getForEntity(url, SearchParamsResponseDTO.class);
		log.info(Constants.Logs.SRV_QUERY_RESPONSE, response.getStatusCode());
		log.debug("[EDS QUERY] Calling for SearchParameters - END");
		return response.getBody();
	}
       
}
