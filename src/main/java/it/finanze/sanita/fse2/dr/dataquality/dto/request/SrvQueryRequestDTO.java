package it.finanze.sanita.fse2.dr.dataquality.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SrvQueryRequestDTO {
	
	private String code;
	
	private String system;
	
	private String targetSystem;

}
