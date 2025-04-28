/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.dr.dataquality.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ValidationResultDTO {

    private boolean isValid;
    private String message;
    private final List<String> normativeR4Messages;
    private final List<String> notTraversedResources;

    public void initialize() {
        this.isValid = getValid();
        this.message = createMessage();
    }

    public ValidationResultDTO() {
        this.normativeR4Messages = new ArrayList<>();
        this.notTraversedResources = new ArrayList<>();
    }

    public boolean getValid() {
        return getNormativeR4Messages().isEmpty() && getNotTraversedResources().isEmpty();
    }

    public String createMessage() {
        String out = "The JSON bundle has been validated";
        return getValid() ? out : getErrorMessage(normativeR4Messages, notTraversedResources);
    }

    private String getErrorMessage(List<String> normative, List<String> graph) {
        StringBuilder sb = new StringBuilder("Unable to validate JSON bundle due to ");
        if (!normative.isEmpty()) {
            sb.append("normative errors: ");
            sb.append(normative);
            sb.append(" ");
        }
        if (!graph.isEmpty()) {
            sb.append("untraversable bundle resources: ");
            sb.append(graph);
        }
        return sb.toString();
    }
}
