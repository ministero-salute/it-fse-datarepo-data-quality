package it.finanze.sanita.fse2.dr.dataquality.service;

import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;

public interface IValidationSRV {

	ValidationResultDTO validateBundle(String bundle);
	
}
