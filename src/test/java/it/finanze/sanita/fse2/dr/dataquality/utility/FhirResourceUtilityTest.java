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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
class FhirResourceUtilityTest {
    
    @MockBean
    private BundleEntryComponent bundleEntry;

    @MockBean
    private IBaseReference baseReference;

    @MockBean
    private IBaseResource baseResource;

    @MockBean
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
