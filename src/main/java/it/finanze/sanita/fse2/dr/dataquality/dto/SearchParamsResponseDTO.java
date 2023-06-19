/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchParamsResponseDTO {

	private final List<SearchParamResourceDTO> params;

	public SearchParamsResponseDTO() {
		this.params = new ArrayList<>();
	}
}
