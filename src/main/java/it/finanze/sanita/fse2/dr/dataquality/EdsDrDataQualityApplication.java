package it.finanze.sanita.fse2.dr.dataquality;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class })
@SpringBootApplication
public class EdsDrDataQualityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdsDrDataQualityApplication.class, args);
	}

	/**
     * Definizione rest template.
     * 
     * @return	rest template
     */
    @Bean 
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    } 

}
