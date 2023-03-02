package it.finanze.sanita.fse2.dr.dataquality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamsResponseDTO {
	private List<SearchParamResourceDTO> params;
	
	public List<SearchParamResourceDTO> getParams() {
		if (params == null) params = new ArrayList<>();
		return params;
	}
	
}
