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
