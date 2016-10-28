package ch.hftm.sax;

import java.io.FileInputStream;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sun.xml.internal.ws.message.RootElementSniffer;

public class SAXParser {
	
	public SAXParser(String fileName, Boolean validate) throws SAXException, IOException {
		InputSource inputSource = new InputSource(new FileInputStream(fileName));
		ContentHandler contentHandler = new UserContentHandler();
		
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();

		xmlReader.setFeature("http://xml.org/sax/features/validation", validate);
		xmlReader.setContentHandler(contentHandler);
		xmlReader.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				if(systemId.contains("html.dtd")) {
					return new InputSource("src/main/resources/xml/html.dtd");
				} else {
				return null;
				}
			}
		});

		xmlReader.parse(inputSource);
	}
	
	public static void main(String[] args) throws SAXException, IOException {
		SAXParser saxParser = new SAXParser("src/main/resources/xml/html.xml", true);
	}

}
