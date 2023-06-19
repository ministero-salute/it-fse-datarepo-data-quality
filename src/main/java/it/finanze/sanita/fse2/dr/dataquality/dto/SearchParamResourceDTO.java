/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchParamResourceDTO {
	private String name;
	private List<String> parameters;
}
