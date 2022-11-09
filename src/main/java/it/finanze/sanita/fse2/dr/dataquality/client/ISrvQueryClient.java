/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.client;

import it.finanze.sanita.fse2.dr.dataquality.dto.request.SrvQueryRequestDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.response.SrvQueryResponseDTO;

public interface ISrvQueryClient {
	
	SrvQueryResponseDTO translate(final SrvQueryRequestDTO req);

}
