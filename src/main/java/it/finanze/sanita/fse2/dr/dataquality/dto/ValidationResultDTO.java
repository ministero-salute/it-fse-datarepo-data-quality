package it.finanze.sanita.fse2.dr.dataquality.dto;

import lombok.Data;

@Data
public class ValidationResultDTO {

	private boolean isValid;
	
	private String message;
	
}
