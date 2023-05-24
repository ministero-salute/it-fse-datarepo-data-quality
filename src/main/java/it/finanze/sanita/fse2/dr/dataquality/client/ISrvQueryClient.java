/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.client;

import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;

/**
 * Interface of Eds client.
 */
public interface ISrvQueryClient {
	
    /**
     * EDS SRV Query - get SearchParams managed by the ServerFHIR 
     */
    SearchParamsResponseDTO getSearchParams() throws Exception;
    
}
