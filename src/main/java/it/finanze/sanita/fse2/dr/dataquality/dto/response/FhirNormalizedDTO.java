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
	private String masterIdentifier;
	private String jsonString;
	private boolean normalized;
}
