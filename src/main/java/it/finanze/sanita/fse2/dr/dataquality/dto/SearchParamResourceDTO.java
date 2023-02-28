package it.finanze.sanita.fse2.dr.dataquality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchParamResourceDTO {
	private String resourceName;
	private List<SearchParamDTO> searchParams;
	
	public List<SearchParamDTO> getSearchParams() {
		if (searchParams == null) searchParams = new ArrayList<>();
		return searchParams;
	}
	
}
