package it.finanze.sanita.fse2.dr.dataquality.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BundleCFG {

    @Value("${bundle.traverse-resources}")
    private boolean traverseResources;

}
