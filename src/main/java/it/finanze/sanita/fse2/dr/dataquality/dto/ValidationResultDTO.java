/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto;

import lombok.Data;

@Data
public class ValidationResultDTO {

	private boolean isValid;
	
	private String message;
	
}
