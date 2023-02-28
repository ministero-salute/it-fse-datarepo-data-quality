package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import org.hl7.fhir.instance.model.api.IBaseResource;

import lombok.Data;

@Data
public class ReferenceDTO {
	private IBaseResource target;
	private String path;
}
