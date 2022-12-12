/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogTraceInfoDTO {

	/**
	 * Span id.
	 */
	private final String spanID;
	
	/**
	 * Trace id.
	 */
	private final String traceID;

}
