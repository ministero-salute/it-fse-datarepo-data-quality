/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.controller.impl;

import javax.servlet.http.HttpServletRequest;

import it.finanze.sanita.fse2.dr.dataquality.dto.tools.RunSchedulerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.dr.dataquality.controller.IValidateCTL;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ValidateCTL implements IValidateCTL {

	@Autowired
	private IValidationSRV validationSRV;
	  
	@Override
	public ValidationResultDTO validateBundle(FhirOperationDTO requestBody,HttpServletRequest request) {
		log.info("Call validate bundle");
		return validationSRV.validateBundle(requestBody.getJsonString());
	}

}