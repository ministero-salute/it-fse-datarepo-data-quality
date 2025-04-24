package it.finanze.sanita.fse2.dr.dataquality.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Data
public class ValidatorFhirCfg {

    @Value("${validator.fhir.terminology-server-url}")
    private String terminologyServerUrl;

    @Value("${validator.fhir.allow-list-igs}")
    private List<String> allowListIgs;

    @Value("${validator.fhir.user-path}")
    private String userPath;

}
