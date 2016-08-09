package ca.tuatara.stackoverflow;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Stackoverflow38838926Application {
	public static void main(String[] args) {
		SpringApplication.run(Stackoverflow38838926Application.class, args);
	}

	@Component
	public class JerseyConfig extends ResourceConfig {
		public JerseyConfig() {
			register(MultiPartFeature.class);
			register(MultipartHandler.class);
			register(MultipartPartsHandler.class);
		}
	}

	@Component
	@Path("/upload")
	@Consumes("multipart/*")
	@Produces("text/text")
	public class MultipartHandler {
		@POST
		public String upload(MultiPart request) {
			StringBuffer response = new StringBuffer();
			for (BodyPart part : request.getBodyParts()) {
				if (MediaType.APPLICATION_JSON_TYPE.isCompatible(part.getMediaType())) {
					response.append(part.getEntityAs(JsonModel.class));
				} else if (MediaType.APPLICATION_XML_TYPE.isCompatible(part.getMediaType())) {
					response.append(part.getEntityAs(XmlModel.class));
				}
				response.append(System.lineSeparator());
			}
			return response.toString();
		}
	}

	@Component
	@Path("/uploadParts")
	@Consumes("multipart/*")
	@Produces("text/text")
	public class MultipartPartsHandler {
		@POST
		public String upload(@FormDataParam("json") JsonModel json, @FormDataParam("xml") XmlModel xml) {
			return json + System.lineSeparator() + xml;
		}
	}
}
