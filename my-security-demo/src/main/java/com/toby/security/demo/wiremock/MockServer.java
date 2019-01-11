package com.toby.security.demo.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author Toby.li
 */
public class MockServer {
	public static void main(String[] args)  throws IOException {
		configureFor(8062);
		removeAllMappings();
		
		myMock("/txtOrder/1","1");
		myMock("/txtOrder/2","2");
	}
	private static void myMock(String apiUrl, String txtfile) throws IOException {
		ClassPathResource resource = new ClassPathResource("mock/response/" + txtfile + ".txt");
		String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8"), "\n");
		stubFor(get(urlPathEqualTo(apiUrl)).willReturn(aResponse().withBody(content).withStatus(200)));
	}
}
