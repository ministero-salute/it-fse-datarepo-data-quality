/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.controller.handler;

import brave.Tracer;
import it.finanze.sanita.fse2.dr.dataquality.dto.LogTraceInfoDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.error.ErrorBuilderDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.exceptions.SchedulerRunningException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionCTL extends ResponseEntityExceptionHandler {

    @Autowired
    private Tracer tracer;

    @ExceptionHandler(SchedulerRunningException.class)
    protected ResponseEntity<ErrorResponseDTO> handleSchedulerRunningException(SchedulerRunningException ex) {
        // Log me
        log.error("HANDLER handleSchedulerRunningException()", ex);
        // Create error DTO
        ErrorResponseDTO out = ErrorBuilderDTO.createSchedulerRunningError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return new ResponseEntity<>(out, headers, out.getStatus());
    }

    /**
     * Generate a new {@link LogTraceInfoDTO} instance
     * @return The new instance
     */
    private LogTraceInfoDTO getLogTraceInfo() {
        // Create instance
        LogTraceInfoDTO out = new LogTraceInfoDTO(null, null);
        // Verify if context is available
        if (tracer.currentSpan() != null) {
            out = new LogTraceInfoDTO(
                tracer.currentSpan().context().spanIdString(),
                tracer.currentSpan().context().traceIdString());
        }
        // Return the log trace
        return out;
    }

}
