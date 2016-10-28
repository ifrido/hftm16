package ch.hftm.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Transform {
	//Working.. But not indenting at the moment
	public void transform(String inputfile) throws Exception {
		Transformer t = TransformerFactory.newInstance().newTransformer();
		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new InputStreamReader(new FileInputStream(inputfile))));
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		
		Source s = new DOMSource(d);
		Result r = new StreamResult(new File("src/main/resources/xml/transform.xml"));
		
		t.transform(s, r);
		t.transform(s, new StreamResult(System.out));
	}
	
}
