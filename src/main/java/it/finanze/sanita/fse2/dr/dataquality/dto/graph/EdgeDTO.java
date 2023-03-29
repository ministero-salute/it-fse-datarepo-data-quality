package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDTO {
	private NodeDTO source;
	private NodeDTO target;
	private String path;
	
	public EdgeDTO(NodeDTO source, ReferenceDTO reference) {
		this(source, new NodeDTO(reference.getTarget()), reference.getPath());
	}
	
}