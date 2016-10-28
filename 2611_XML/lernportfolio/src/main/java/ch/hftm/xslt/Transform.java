package ch.hftm.xslt;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transform {
	private static String xmlPath = "src/main/resources/xml/xsl.xml";
    private static String xslPath = "src/main/resources/xml/xsl.xsl";
    private static String destinationPath = "src/main/resources/xml/transformed.xml";

    /**
     * Akzeptiert zwei Kommandozeilenparameter: Den Namen einer XMl-Datei und
     * den Namen eines XSL-Stylesheets. Das Ergebnis der Transformation wird
     * ausgegeben.
     * 
     * @ Proudly copied from Simeon Liniger
     */
    public static void main(String[] args) throws TransformerException {
        // Kapselt die benötigten Dateien als XML-Streams
        // .. für den nachfolgenden Transformationsprozess.
        // .. (arg[0]: XML-Quelldokument
        // .. (arg[1]: XSL-Stylesheet
        Source xmlSrc = new StreamSource(new File(xmlPath));
        Source xslSrc = new StreamSource(new File(xslPath));
        // Kapselt das Transformationsresultat in Form eines
        // .. Markup-Streams.
        Result ergebnis = new StreamResult(new File(destinationPath));

        // Eine Instanz der (Standard-)TransformerFactory erzeugen
        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();

        // Beschaffung einer XSLT-Prozessor-Instanz unter
        // Angabe des zu verarbeitenden Stylesheets.
        Transformer transformer = transformerFactory.newTransformer(xslSrc);

        // .. Transformation eines Quellbaumes in einen
        // .. Resultatbaum.
        transformer.transform(xmlSrc, ergebnis);

    }

}
