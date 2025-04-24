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
package it.finanze.sanita.fse2.dr.dataquality.service;

import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;

public interface IValidationSRV {

    /**
     * Validates the supplied FHIR Bundle JSON string and assesses its graph
     * traversability.
     *
     * <p>
     * This method first invokes the FHIR validator to perform normative R4
     * validation on the bundle, collecting all ERROR- and FATAL-level issues.
     * It then traverses the resource graph to identify any resources that were
     * not reached during traversal.
     *
     * @param bundle the JSON representation of the FHIR Bundle to validate
     * @return a {@link ValidationResultDTO} containing:
     *         <ul>
     *         <li>the list of normative R4 validation messages (errors and
     *         fatals)</li>
     *         <li>the list of resource references that were not traversed</li>
     *         </ul>
     */
    ValidationResultDTO validateBundle(String bundle);

}
