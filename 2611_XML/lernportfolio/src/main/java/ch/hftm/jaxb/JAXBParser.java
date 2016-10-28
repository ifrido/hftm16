package ch.hftm.jaxb;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.xml.sax.InputSource;

public class JAXBParser {

	public static void main(String[] args) throws Exception {

		InputSource inputSource = new InputSource(new FileInputStream("src/main/resources/xml/html.xml"));
		JAXBContext context = JAXBContext.newInstance(Element.class);
		Unmarshaller um = context.createUnmarshaller();
		Element e = (Element) um.unmarshal(inputSource);

		//convert(e.getContainers().get(0));

		
		 Marshaller m = context.createMarshaller();
		 m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
		 m.marshal(e,System.out);
		 
	}

	/**
	 * Failed attempt to create recursive listing of elemnts over the objects
	 */
	/*public static void convert(Container c) {
		Container currentContainer = c;
		Include currentInclude;
		List<Container> containerList = c.getContainers();
		List<Include> includeList = c.getIncludes();

		for (int i = 0; i < containerList.size(); i++) {
			System.out.println("<" + currentContainer.getAttributeMap().get(new QName("name")) + ">");
			currentContainer = containerList.get(i);
			if (includeList.size() > 0) {
				for (int j = 0; j < includeList.size(); j++) {
					System.out.println("<" + includeList.get(j).getAttributeMap().get(new QName("name")) + ">");
				}
			}
		}
		System.out.println("</" + currentContainer.getAttributeMap().get(new QName("name")) + ">");
	}*/

	/* public static void convert(Include i) {
		Include currentInclude = i;
		System.out.println("<" + currentInclude.getAttributeMap().get(new QName("name")) + ">");

	} */
}
