package ch.hftm.jaxb;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
public class Container {
	@XmlAnyAttribute
	private Map<QName, String> attributeMap;
	@XmlElement(name="include")
	private List<Include> includes;
	@XmlElement(name="container")
	private List<Container> containers;

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public List<Include> getIncludes() {
		return includes;
	}

	public void setIncludes(List<Include> includes) {
		this.includes = includes;
	}

	public Map<QName, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<QName, String> attributeMap) {
		this.attributeMap = attributeMap;
	}
}
