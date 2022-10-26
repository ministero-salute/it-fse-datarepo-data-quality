package it.finanze.sanita.fse2.dr.dataquality.controller.impl;

import javax.servlet.http.HttpServletRequest;

import it.finanze.sanita.fse2.dr.dataquality.dto.response.FhirNormalizedDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.dr.dataquality.controller.IControllerDataQuality;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.FhirOperationDTO;

@RestController
public class ControllerDataQuality implements IControllerDataQuality {

	@Override
	public Object getDataQuality(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<FhirNormalizedDTO> normalize(FhirOperationDTO requestBody, HttpServletRequest request) {

		ResponseEntity<FhirNormalizedDTO> output = null;
//		try {

		if (requestBody.getJsonString() != null && requestBody.getMasterIdentifier() != null) {

		} else {

		}
//		 output = new ResponseEntity<>(state, HttpServletRequest);
//		} catch (Exception e) {
//		output = new ResponseEntity<>(null, HttpServletRequest);
//		}
		return new ResponseEntity<>(
				FhirNormalizedDTO.builder()
						.normalized(true)
						.masterIdentifier(requestBody.getMasterIdentifier())
						.jsonString(requestBody.getJsonString())
						.build(),
				HttpStatus.OK);
	}

	@Override
	public Object deleteDataQuality(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object putDataQuality(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}