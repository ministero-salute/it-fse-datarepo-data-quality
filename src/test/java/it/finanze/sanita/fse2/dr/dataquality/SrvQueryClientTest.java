package it.finanze.sanita.fse2.dr.dataquality;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.finanze.sanita.fse2.dr.dataquality.client.impl.SrvQueryClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SrvQueryClientTest extends AbstractTest{
	
	@Autowired
	SrvQueryClient client;
	
	@Test
	void clientSrvQueryOtherTest() {
		client.translate(buildReqDTO("code", "system", "targetSystem"));
		
	}
}
