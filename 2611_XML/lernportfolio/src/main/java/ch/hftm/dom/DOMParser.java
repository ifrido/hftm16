package ch.hftm.dom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.hftm.transformer.Transform;

public class DOMParser {
	public static List<String> lines = new Stack<String>();

	public static void main(String[] args) throws Exception {
		File xmlFile = new File("src/main/resources/xml/html.xml");
		Transform t = new Transform();
		DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
		DocumentBuilder dB = dbF.newDocumentBuilder();
		Document d = dB.parse(xmlFile);
		
		convert(d.getDocumentElement());
	    Files.write(Paths.get("src/main/resources/xml/result.xml"), lines);
	    t.transform("src/main/resources/xml/result.xml");
	}
	
	public static void convert(Node node) throws DOMException, SAXException, IOException, ParserConfigurationException {
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	        	switch(currentNode.getNodeName()) {
	        	case "include":
	        		convert(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("src/main/resources/xml/" + currentNode.getAttributes().getNamedItem("name").getNodeValue())).getDocumentElement());
	        		break;
	        	default:
	        		System.out.println("<" + currentNode.getAttributes().getNamedItem("name").getNodeValue() + ">");
	        		lines.add("<" + currentNode.getAttributes().getNamedItem("name").getNodeValue() + ">");
		            convert(currentNode);
		            break;
	        	}
	        	
	        }
	        
	    }
	    if(node.hasAttributes()) {
	    System.out.println("</" + node.getAttributes().getNamedItem("name").getNodeValue() + ">");
	    lines.add("</" + node.getAttributes().getNamedItem("name").getNodeValue() + ">");
	    }
	}
}
