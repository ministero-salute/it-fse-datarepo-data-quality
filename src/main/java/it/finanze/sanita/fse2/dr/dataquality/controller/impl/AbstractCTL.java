/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.controller.impl;

import brave.Tracer;
import it.finanze.sanita.fse2.dr.dataquality.dto.LogTraceInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *	Abstract controller.
 */
public abstract class AbstractCTL {

	@Autowired
	private Tracer tracer;
   
	protected LogTraceInfoDTO getLogTraceInfo() {
		LogTraceInfoDTO out = new LogTraceInfoDTO(null, null);
		if (tracer.currentSpan() != null) {
			out = new LogTraceInfoDTO(
					tracer.currentSpan().context().spanIdString(), 
					tracer.currentSpan().context().traceIdString());
		}
		return out;
	}
}
