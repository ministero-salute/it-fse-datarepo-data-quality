/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SrvQueryResponseDTO extends ResponseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5590526247114514251L;

	private String result;
	
	private String traceID;
	
	private String spanID;


	public SrvQueryResponseDTO() {
		super();
	}

	public SrvQueryResponseDTO(final LogTraceInfoDTO traceInfo, final String inResult) {
		traceID = traceInfo.getTraceID();
		spanID = traceInfo.getSpanID();
		result = inResult;
	}

	public SrvQueryResponseDTO(final LogTraceInfoDTO traceInfo) {
		traceID = traceInfo.getTraceID();
		spanID = traceInfo.getSpanID(); 
	}
	
}
