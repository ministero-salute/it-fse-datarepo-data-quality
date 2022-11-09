/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FhirOperationDTO {
	
	private String masterIdentifier;
	private String jsonString;
	
	public String getMasterIdentifier() {
		return this.masterIdentifier;
	};
	
	public String getJsonString() {
		return this.jsonString;
	};
}
