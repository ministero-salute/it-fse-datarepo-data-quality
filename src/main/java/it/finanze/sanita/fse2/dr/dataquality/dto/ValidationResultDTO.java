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
		String out = "The JSON bundle has been validated";
		return isValid() ? out : getErrorMessage(normativeR4Messages, notTraversedResources);
	}

	private String getErrorMessage(List<String> normative, List<String> graph) {
		StringBuilder sb = new StringBuilder("Unable to validate JSON bundle due to ");
		if(!normative.isEmpty() && !graph.isEmpty()) {
			sb.append("normative errors and untraversable bundle resources");
		} else if(!normative.isEmpty()) {
			sb.append("normative errors");
		} else if(!graph.isEmpty()) {
			sb.append("untraversable bundle resources");
		} else {
			sb.append("unknown reasons");
		}
		return sb.toString();
	}
}
