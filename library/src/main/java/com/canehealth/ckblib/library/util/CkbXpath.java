package com.canehealth.ckblib.library.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.StringReader;

@Data
@AllArgsConstructor
public class CkbXpath {

    private String XmlString;

    public NodeList getNodes(String xmlPath){
        XPath xPath = XPathFactory.newInstance().newXPath();
        Document doc = convertStringToXMLDocument(this.XmlString);
        try {
            NodeList nodeList = (NodeList) xPath.compile(xmlPath).evaluate(doc, XPathConstants.NODESET);
            return nodeList;
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Document convertStringToXMLDocument(String xmlString) {
        // Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            // Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
