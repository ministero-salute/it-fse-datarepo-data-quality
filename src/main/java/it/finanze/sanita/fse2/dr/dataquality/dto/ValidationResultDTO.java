/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationResultDTO {

	private List<String> invalidHttpMethods;
	private List<String> normativeR4Messages;
	private List<String> notTraversedResources;

	public List<String> getInvalidHttpMethods() {
		if (invalidHttpMethods == null) invalidHttpMethods = new ArrayList<>();
		return invalidHttpMethods;
	}
	
	public List<String> getNormativeR4Messages() {
		if (normativeR4Messages == null) normativeR4Messages = new ArrayList<>();
		return normativeR4Messages;
	}
	
	public List<String> getNotTraversedResources() {
		if (notTraversedResources == null) notTraversedResources = new ArrayList<>();
		return notTraversedResources;
	}
	
	public boolean isValid() {
		return getInvalidHttpMethods().isEmpty() && getNormativeR4Messages().isEmpty() && getNotTraversedResources().isEmpty();
	}
	
	public String getMessage() {
		return "";
	}
}
