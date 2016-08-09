package ca.tuatara.stackoverflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Stackoverflow38838926ApplicationTests {
	@LocalServerPort
	private int port;

	private ObjectMapper objectMapper;
	private XmlMapper xmlMapper;
	private CloseableHttpClient httpClient;

	@Before
	public void before() {
		objectMapper = new ObjectMapper();

		JaxbAnnotationModule module = new JaxbAnnotationModule();
		xmlMapper = new XmlMapper();
		xmlMapper.registerModule(module);

		httpClient = HttpClients.createDefault();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMultipartHandlerUpload() throws Exception {
		JsonModel jsonModel = new JsonModel();
		jsonModel.setJson("payload");
		XmlModel xmlModel = new XmlModel();
		xmlModel.setTest("blah");

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("json", objectMapper.writeValueAsString(jsonModel), ContentType.APPLICATION_JSON);
		builder.addTextBody("xml", xmlMapper.writeValueAsString(xmlModel), ContentType.APPLICATION_XML);
		HttpEntity multipart = builder.build();

		HttpPost upload = new HttpPost("http://localhost:" + port + "/upload");
		upload.setEntity(multipart);
		CloseableHttpResponse response = httpClient.execute(upload);

		HttpEntity entity = response.getEntity();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
			assertThat(reader.readLine()).isEqualTo(jsonModel.toString());
			assertThat(reader.readLine()).isEqualTo(xmlModel.toString());
		}
	}

	@Test
	public void testMultipartPartsHandlerUpload() throws Exception {
		JsonModel jsonModel = new JsonModel();
		jsonModel.setJson("payload");
		XmlModel xmlModel = new XmlModel();
		xmlModel.setTest("blah");

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("json", objectMapper.writeValueAsString(jsonModel), ContentType.APPLICATION_JSON);
		builder.addTextBody("xml", xmlMapper.writeValueAsString(xmlModel), ContentType.APPLICATION_XML);
		HttpEntity multipart = builder.build();

		HttpPost upload = new HttpPost("http://localhost:" + port + "/uploadParts");
		upload.setEntity(multipart);
		CloseableHttpResponse response = httpClient.execute(upload);

		HttpEntity entity = response.getEntity();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
			assertThat(reader.readLine()).isEqualTo(jsonModel.toString());
			assertThat(reader.readLine()).isEqualTo(xmlModel.toString());
		}
	}
}
