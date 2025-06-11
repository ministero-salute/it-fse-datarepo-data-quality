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
package it.finanze.sanita.fse2.dr.dataquality.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.hl7.fhir.instance.model.api.IBaseReference;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.IdType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
class FhirResourceUtilityTest {

    @MockitoBean
    private BundleEntryComponent bundleEntry;

    @MockitoBean
    @MockitoBean
    private IBaseReference baseReference;

    @MockitoBean
    @MockitoBean
    private IBaseResource baseResource;

    @MockitoBean
    @MockitoBean
    private IIdType idType;

    @Test
    void getResourceAsStringFromReferenceTest() {
        // Mock
        when(baseReference.getResource()).thenReturn(baseResource);
        when(baseResource.getIdElement()).thenReturn(new IdType("Test", "456"));
        // Perform method
        String result = FhirResourceUtility.getResourceAsString(baseReference);
        // Assertion
        assertEquals("Test/456", result);
    }

    @Test
    void getReferenceAsStringtTest() {
        // Mock
        when(baseReference.getReferenceElement()).thenReturn(idType);
        when(idType.getResourceType()).thenReturn("Test");
        when(idType.getIdPart()).thenReturn("789");
        // Perform method
        String result = FhirResourceUtility.getReferenceAsString(baseReference);
        // Assertion
        assertEquals("Test/789", result);
    }

    @Test
    void getResourceAsStringFromResourceTest() {
        // Mock
        when(baseResource.getIdElement()).thenReturn(new IdType("Test", "101"));
        // Perform method
        String result = FhirResourceUtility.getResourceAsString(baseResource);
        // Assertion
        assertEquals("Test/101", result);
    }

    @Test
    void getIdTypeAsStringTest() {
        // Mock
        when(idType.getResourceType()).thenReturn("Test");
        when(idType.getIdPart()).thenReturn("222");
        // Perform method
        String result = FhirResourceUtility.getIdTypeAsString(idType);
        // Assertion
        assertEquals("Test/222", result);
    }

    @Test
    void getIdTypeAsStringNullIdTypeTest() {
        // Perform method
        String result = FhirResourceUtility.getIdTypeAsString(null);
        // Assertion
        assertEquals(null, result);
    }

}
