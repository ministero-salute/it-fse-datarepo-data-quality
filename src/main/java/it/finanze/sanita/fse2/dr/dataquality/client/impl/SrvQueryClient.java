package it.finanze.sanita.fse2.dr.dataquality.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import it.finanze.sanita.fse2.dr.dataquality.client.ISrvQueryClient;
import it.finanze.sanita.fse2.dr.dataquality.client.exceptions.BusinessException;
import it.finanze.sanita.fse2.dr.dataquality.client.exceptions.RecordNotFoundException;
import it.finanze.sanita.fse2.dr.dataquality.client.exceptions.ServerResponseException;
import it.finanze.sanita.fse2.dr.dataquality.config.MicroservicesURLConfig;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.SrvQueryRequestDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.response.SrvQueryResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.enums.SrvQueryErrors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SrvQueryClient implements ISrvQueryClient {

	@Autowired
	private ServletWebServerApplicationContext webServerAppCtxtApplicationContext;

	@Autowired
	private MicroservicesURLConfig urlCFG;

	@Autowired
	private transient RestTemplate restTemplate;

	@Override
	public SrvQueryResponseDTO translate(SrvQueryRequestDTO req) {

		SrvQueryResponseDTO output = null;

		try {

			String url = "http://localhost:" + webServerAppCtxtApplicationContext.getWebServer().getPort()
					+ "/v1/terminology-service/conceptMap/translate";

			UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("code", req.getCode())
					.queryParam("system", req.getSystem()).queryParam("targetSystem", req.getTargetSystem()).build();

			HttpEntity<String> requestEntity = new HttpEntity<>(null, new HttpHeaders());

			ResponseEntity<SrvQueryResponseDTO> strResponse = restTemplate.exchange(builder.toUriString(),
					HttpMethod.GET, requestEntity, SrvQueryResponseDTO.class);

			// Gestione response
			if (HttpStatus.OK.equals(strResponse.getStatusCode()) && strResponse.getBody() != null) {
				output = strResponse.getBody();
				if (output != null) {

				} else {
					if (output.getResult().equals(SrvQueryErrors.RECORD_NOT_FOUND.toString())) {
						throw new RecordNotFoundException(output.getResult());
					}
					throw new BusinessException(output.getResult());
				}
			}
		} catch (RecordNotFoundException e0) {
			throw e0;
		} catch (BusinessException e1) {
			throw e1;
		} catch (HttpClientErrorException e2) {
			errorHandler(e2, "/v1/terminology-service/conceptMap/translate");
		} catch (Exception e) {
			log.error("Errore durante l'invocazione dell' API translate(). ", e);
			throw new BusinessException("Errore durante l'invocazione dell' API translate(). ", e);
		}

		return output;
	}

	/**
	 * Error handler.
	 *
	 * @param e1       the e 1
	 * @param endpoint the endpoint
	 */
	private void errorHandler(HttpStatusCodeException e1, String endpoint) {
		// Generic handler
		String msg = "Errore durante l'invocazione dell' API " + endpoint + ". Il sistema ha restituito un "
				+ e1.getStatusCode();
		throw new ServerResponseException(endpoint, msg, e1.getStatusCode(), e1.getRawStatusCode(),
				e1.getLocalizedMessage());
	}

	/**
	 * Builder endpoint Settings API.
	 *
	 * @param endpoint the endpoint
	 * @return the string
	 */
	private String buildEndpoint(final String endpoint) {
		// Build dell'endpoint da invocare.
		StringBuilder sb = new StringBuilder(urlCFG.getQuerySrvHost()); // Base URL host
		sb.append(endpoint);
		return sb.toString();
	}

}
