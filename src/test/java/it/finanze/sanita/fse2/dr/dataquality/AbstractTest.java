/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality;



import it.finanze.sanita.fse2.dr.dataquality.dto.request.SrvQueryRequestDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTest {

	protected SrvQueryRequestDTO buildReqDTO(String code, String system, String targetSystem) {
		SrvQueryRequestDTO queryReq = SrvQueryRequestDTO.builder().
				code("code").
				system("system").targetSystem("targetSystem").
				build(); 
		
		return queryReq;
	}
}
