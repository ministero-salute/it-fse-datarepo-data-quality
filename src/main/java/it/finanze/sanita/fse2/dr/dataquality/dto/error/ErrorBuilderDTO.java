/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.error;


import it.finanze.sanita.fse2.dr.dataquality.dto.LogTraceInfoDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.exceptions.SchedulerRunningException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.apache.http.HttpStatus.SC_LOCKED;

/**
 * Builder class converting a given {@link Exception} into its own {@link ErrorResponseDTO} representation
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorBuilderDTO {
    public static ErrorResponseDTO createSchedulerRunningError(LogTraceInfoDTO trace, SchedulerRunningException ex) {
        // Return associated information
        return new ErrorResponseDTO(
            trace,
            ErrorType.IO.getType(),
            ErrorType.IO.getTitle(),
            ex.getMessage(),
            SC_LOCKED,
            ErrorType.IO.toInstance(ErrorInstance.IO.QUEUE)
        );
    }
}
