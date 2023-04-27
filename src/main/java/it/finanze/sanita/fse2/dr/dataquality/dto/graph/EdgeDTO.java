package it.finanze.sanita.fse2.dr.dataquality.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdgeDTO {
	private NodeDTO source;
	private NodeDTO target;
	private String path;
	private boolean traversed;
	private boolean searchParam;

	public EdgeDTO(NodeDTO source, ReferenceDTO reference) {
		this(source, new NodeDTO(reference.getTarget()), reference.getPath(), false, false);
	}

	public boolean isNotTraversed() {
		return !traversed;
	}

	@Override
	public String toString() {
		return source.getType() + "^" + target.getType() + "^" + path;
	}

}