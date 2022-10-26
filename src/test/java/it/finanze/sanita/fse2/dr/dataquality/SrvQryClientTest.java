package it.finanze.sanita.fse2.dr.dataquality;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.mongodb.assertions.Assertions;

import it.finanze.sanita.fse2.dr.dataquality.client.impl.SrvQueryClient;
import it.finanze.sanita.fse2.dr.dataquality.dto.request.SrvQueryRequestDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.response.SrvQueryResponseDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ComponentScan
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DisplayName("SrvQueryClient Unit Test")
public class SrvQryClientTest {

	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private SrvQueryClient client;

	@Autowired
	private ServletWebServerApplicationContext webServerAppCtxtApplicationContext;

	@Test
	@DisplayName("client response")
	void respClient() throws Exception {

		SrvQueryRequestDTO req = new SrvQueryRequestDTO();

		HttpEntity<String> requestEntity = new HttpEntity<>(null, new HttpHeaders());

		SrvQueryResponseDTO res = new SrvQueryResponseDTO();

		res.setResult("Result");
		res.setSpanID("SpanID");
		res.setTraceID("TraceID");

		ResponseEntity<SrvQueryResponseDTO> responseEntity = new ResponseEntity<SrvQueryResponseDTO>(res,
				HttpStatus.OK);

		String url = "http://localhost:" + webServerAppCtxtApplicationContext.getWebServer().getPort()
				+ "/v1/terminology-service/conceptMap/translate";

		UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("code", req.getCode())
				.queryParam("system", req.getSystem()).queryParam("targetSystem", req.getTargetSystem()).build();

		// given
		given(restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, SrvQueryResponseDTO.class))
				.willReturn(responseEntity);

		// when
		SrvQueryResponseDTO responseDTO = client.translate(req);

		// then
		assertNotNull(responseDTO, "response null");
		assertNotNull(res.getResult(), "Result is null");
		assertNotNull(res.getSpanID(), "SpanID is null");
		assertNotNull(res.getTraceID(), "LogTraceID is(null)");
		Assertions.assertFalse(res.getResult().isEmpty());
		assertEquals(res.getResult(), responseDTO.getResult());

	}

}
