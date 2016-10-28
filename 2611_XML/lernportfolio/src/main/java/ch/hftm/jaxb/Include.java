package ch.hftm.jaxb;

import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.namespace.QName;

public class Include {
	@XmlAnyAttribute
	private Map<QName, String> attributeMap;

	public Map<QName, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<QName, String> attributeMap) {
		this.attributeMap = attributeMap;
	}
}
