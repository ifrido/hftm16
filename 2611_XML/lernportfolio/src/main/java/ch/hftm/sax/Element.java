package ch.hftm.sax;

import java.util.HashMap;
import java.util.Map;

public class Element {
	public String name;
	Map<String, String> attributeMap = new HashMap<String, String>();
	
	public Element(String name, Map<String, String> attributeMap) {
		this.name = name;
		this.attributeMap = attributeMap;
	}
	
}
