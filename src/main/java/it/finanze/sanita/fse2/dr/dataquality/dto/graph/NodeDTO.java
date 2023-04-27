package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import it.finanze.sanita.fse2.dr.dataquality.utility.FhirResourceUtility;
import lombok.Data;
import org.hl7.fhir.instance.model.api.IBaseResource;

@Data
public class NodeDTO implements IGraphResourceDTO {
	private IBaseResource resource;
	private String id;
	private String type;
	private boolean traversed;

	public NodeDTO(ReferenceDTO reference) {
		this.resource = reference.getTarget();
		this.id = reference.getTargetReference();
		this.type = resource != null ? resource.getIdElement().getResourceType() : null;
	}

	public NodeDTO(IBaseResource resource) {
		this.resource = resource;
		this.id = FhirResourceUtility.getResourceAsString(resource);
		this.type = resource != null ? resource.getIdElement().getResourceType() : null;
	}

	@Override
	public String toString() {
		return getId();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof NodeDTO)) return false;
		NodeDTO node = (NodeDTO) o;
		if (node.getId() == null && this.getId() != null) return false;
		if (node.getId() != null && this.getId() == null) return false;
		if (this.getId() == null) return false;
		return node.getId().equals(this.getId());
	}

}