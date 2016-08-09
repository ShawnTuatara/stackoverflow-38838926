package ca.tuatara.stackoverflow;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonModel {
	private String json;

	public JsonModel() {
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public String toString() {
		return "JsonModel [json=" + json + "]";
	}
}