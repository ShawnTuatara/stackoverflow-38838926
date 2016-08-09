package ca.tuatara.stackoverflow;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xmlModel")
public class XmlModel {
	private String test;

	public XmlModel() {
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@Override
	public String toString() {
		return "XmlModel [test=" + test + "]";
	}
}