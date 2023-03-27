package it.finanze.sanita.fse2.dr.dataquality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamDTO {
	private String name;
	private String type;
	private String path;
}
