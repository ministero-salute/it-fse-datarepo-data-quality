/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.response;

import it.finanze.sanita.fse2.dr.dataquality.dto.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FhirNormalizedDTO extends AbstractDTO {
	
	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 5101143584909736125L;
	
	private String masterIdentifier;
	
	private String jsonString;
	
	private boolean normalized;
}
