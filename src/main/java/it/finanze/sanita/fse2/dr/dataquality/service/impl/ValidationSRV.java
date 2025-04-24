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
package it.finanze.sanita.fse2.dr.dataquality.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r5.model.OperationOutcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.dr.dataquality.config.BundleCFG;
import it.finanze.sanita.fse2.dr.dataquality.dto.ValidationResultDTO;
import it.finanze.sanita.fse2.dr.dataquality.service.IGraphSRV;
import it.finanze.sanita.fse2.dr.dataquality.service.IValidationSRV;
import it.finanze.sanita.fse2.dr.dataquality.validator.FHIRValidatorHelper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationSRV implements IValidationSRV {

    @Autowired
    private BundleCFG bundleCFG;

    @Autowired
    private IGraphSRV graphSRV;

    @Autowired
    private FHIRValidatorHelper fhirValidator;

    @Override
    public ValidationResultDTO validateBundle(String jsonBundle) {
        ValidationResultDTO result = new ValidationResultDTO();
        OperationOutcome oo = fhirValidator.validate(jsonBundle.getBytes());

        // Create response
        result.getNormativeR4Messages().addAll(fhirValidator.getMessagesFromOutcome(oo));
        result.getNotTraversedResources().addAll(traverseGraph(jsonBundle));
        return result;
    }

    private List<String> traverseGraph(String jsonBundle) {
        if (bundleCFG.isTraverseResources())
            return graphSRV.traverseGraph(jsonBundle);
        log.debug("Skipping traversing bundle resources");
        return new ArrayList<>();
    }

}