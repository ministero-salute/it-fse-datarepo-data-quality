/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class ValidationResultDTO {

	private final List<String> normativeR4Messages;
	private final List<String> notTraversedResources;

	public ValidationResultDTO() {
		this.normativeR4Messages = new ArrayList<>();
		this.notTraversedResources = new ArrayList<>();
	}
	
	public boolean isValid() {
		return getNormativeR4Messages().isEmpty() && getNotTraversedResources().isEmpty();
	}
	
	public String getMessage() {
		return "";
	}
}
