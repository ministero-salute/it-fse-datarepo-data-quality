package it.finanze.sanita.fse2.dr.dataquality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchParamsResponseDTO {
	private List<SearchParamResourceDTO> resources;
	
	public List<SearchParamResourceDTO> getResources() {
		if (resources == null) resources = new ArrayList<>();
		return resources;
	}
	
}
