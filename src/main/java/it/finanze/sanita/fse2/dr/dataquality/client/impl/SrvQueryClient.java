/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
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
    private RestTemplate restTemplate;

    /*
     * Microservices URL Config 
     */
    @Autowired
    private MicroservicesURLCFG microservicesURLCFG;

	@Override
	public SearchParamsResponseDTO getSearchParams() {
		log.debug("[EDS QUERY] Calling for SearchParameters - START");
		ResponseEntity<SearchParamsResponseDTO> response = null;
		String url = microservicesURLCFG.getEdsQueryHost() + "/v1/searchParams";

		try {
			response = restTemplate.getForEntity(url, SearchParamsResponseDTO.class);
			log.info(Constants.Logs.SRV_QUERY_RESPONSE, response.getStatusCode());
		} catch(ResourceAccessException cex) {
			log.error("Connect error while call srv-query for SearchParameters:" + cex);
			throw cex;
		}  
		log.debug("[EDS QUERY] Calling for SearchParameters - END");
		return response.getBody();
	}
       
}
