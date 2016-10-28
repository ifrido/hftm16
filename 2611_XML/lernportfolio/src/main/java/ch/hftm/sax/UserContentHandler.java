package ch.hftm.sax;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class UserContentHandler implements ContentHandler {

	// Add helper objects to get access to the attribute values in the endElement method
	Element currentElement;
	List<Element> elementList = new Stack<Element>();

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

		// Map to save attribute values to element, no values used in this example except 'name'
		Map<String, String> attributeMap = new HashMap<String, String>();

		for (int i = 0; i < atts.getLength(); i++) {
			attributeMap.put(atts.getLocalName(i), atts.getValue(i));
		}

		if (localName.equals("include")) {
			// If an 'include' element occurs recurse into another parser which parses the file
			try {
				// Create the parser but without validation
				SAXParser saxParser = new SAXParser("src/main/resources/xml/" + attributeMap.get("name"), false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// Normal elments are saved to the list and printed out in HTML style, except root elements with no name attribute
			currentElement = new Element(attributeMap.get("name"), attributeMap);
			elementList.add(currentElement);
			if(currentElement.name != null) {
			System.out.println("<" + currentElement.name + ">");
			}
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		// Print the end tag for normal elements, here the element list helps us to retrieve the name attribute
		if (!localName.equals("include")) {
			if(elementList.get(elementList.size() - 1).name != null) {
			System.out.println("</" + elementList.get(elementList.size() - 1).name + ">");
			}
			elementList.remove(elementList.size() - 1);
		}
	}

	// All other methods are not neccessary
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {
			
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {

	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {

	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {

	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {

	}

	@Override
	public void setDocumentLocator(Locator arg0) {

	}

}
