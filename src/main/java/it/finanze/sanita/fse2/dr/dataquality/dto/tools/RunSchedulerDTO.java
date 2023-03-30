/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.tools;

import it.finanze.sanita.fse2.dr.dataquality.dto.LogTraceInfoDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.ResponseDTO;
import lombok.Getter;

@Getter
public class RunSchedulerDTO extends ResponseDTO {

    private final String message;

    public RunSchedulerDTO(LogTraceInfoDTO info, String message) {
        super(info);
        this.message = message;
    }

}
