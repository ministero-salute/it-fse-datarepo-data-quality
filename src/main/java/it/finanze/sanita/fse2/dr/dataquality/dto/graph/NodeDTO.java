package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import org.hl7.fhir.instance.model.api.IBaseResource;

import it.finanze.sanita.fse2.dr.dataquality.utility.FhirResourceUtility;
import lombok.Data;

@Data
public class NodeDTO {
	private IBaseResource resource;
	private String id;
	private String type;
	private boolean traversed;
	
	public NodeDTO(IBaseResource resource) {
		this.resource = resource;
		this.id = FhirResourceUtility.getResourceAsString(resource);
		this.type = resource.getIdElement().getResourceType();
	}

}