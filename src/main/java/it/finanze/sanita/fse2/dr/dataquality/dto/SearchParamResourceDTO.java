package it.finanze.sanita.fse2.dr.dataquality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamResourceDTO {
	private String resourceName;
	private List<SearchParamDTO> parameters;
	
	public List<SearchParamDTO> getParameters() {
		if (parameters == null) parameters = new ArrayList<>();
		return parameters;
	}
}
