package it.finanze.sanita.fse2.dr.dataquality.validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.r5.elementmodel.Manager.FhirFormat;
import org.hl7.fhir.r5.model.OperationOutcome;
import org.hl7.fhir.r5.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.utilities.TimeTracker;
import org.hl7.fhir.validation.IgLoader;
import org.hl7.fhir.validation.ValidationEngine;
import org.hl7.fhir.validation.cli.model.CliContext;
import org.hl7.fhir.validation.cli.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.dr.dataquality.config.ValidatorFhirCfg;
import it.finanze.sanita.fse2.dr.dataquality.exceptions.EngineInitException;
import it.finanze.sanita.fse2.dr.dataquality.utility.StringUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FHIRValidatorHelper {

    private static CliContext cliContext = new CliContext();
    private static ValidationService validateSrv = new ValidationService();
    private static ValidationEngine validateEngine;

    @Getter
    private boolean initialiting;

    @Autowired
    private ValidatorFhirCfg validatorFhirCfg;

    /**
     * Extracts only ERROR and FATAL issues from an R5 OperationOutcome,
     * formatting each as "Severity: <code> Message: <text>".
     */
    public List<String> getMessagesFromOutcome(OperationOutcome outcome) {
        List<IssueSeverity> failures = Arrays.asList(
                IssueSeverity.ERROR,
                IssueSeverity.FATAL);

        return outcome.getIssue().stream()
                .filter(issue -> failures.contains(issue.getSeverity()))
                .map(issue -> {
                    String text = null;
                    if (issue.hasDetails() && issue.getDetails().hasText()) {
                        text = issue.getDetails().getText();
                    } else if (issue.hasDiagnostics()) {
                        text = issue.getDiagnostics();
                    }
                    if (text == null) {
                        text = "(no message provided)";
                    }
                    return String.format(
                            "Severity: %s Message: %s",
                            issue.getSeverity().toCode(),
                            text);
                })
                .collect(Collectors.toList());
    }

    @Async
    @EventListener(ApplicationStartedEvent.class)
    void initialize() {
        initialiting = true;
        try {
            cliContext.setSv("4.0");
            if (!StringUtility.isNullOrEmpty(validatorFhirCfg.getTerminologyServerUrl())) {
                cliContext.setTxServer(null);
            }

            System.setProperty("user.home", validatorFhirCfg.getUserPath());
            validateEngine = validateSrv.initializeValidator(cliContext, "hl7.fhir.r4.core#4.0.1", new TimeTracker());
            loadIgsAndExtensions(validatorFhirCfg.getAllowListIgs());
        } catch (Exception ex) {
            log.error("Error while initialite validator:", ex);
            initialiting = false;
        }

        initialiting = false;
    }

    private void loadIgsAndExtensions(List<String> igs) {
        try {
            IgLoader igLoader = new IgLoader(validateEngine.getPcm(), validateEngine.getContext(),
                    validateEngine.getVersion(), validateEngine.isDebug());
            for (String src : igs) {
                igLoader.loadIg(validateEngine.getIgs(), validateEngine.getBinaries(), src, false);
            }
        } catch (Exception ex) {
            System.out.println("Stop");
        }
    }

    public OperationOutcome validate(byte[] bundle) {
        if (initialiting) {
            throw new EngineInitException("Attenzione, l'engine e' in fase di inizializzazione");
        }

        try (InputStream resourceStream = new ByteArrayInputStream(bundle)) {
            return validateEngine.validate(FhirFormat.JSON, resourceStream, null);
        } catch (Exception ex) {
            return null;
        }
    }

}
