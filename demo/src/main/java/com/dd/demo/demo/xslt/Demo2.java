package com.dd.demo.demo.xslt;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * @author Bryce_dd 2023/8/17 18:50
 */
public class Demo2 {

    public static void main(String[] args) throws TransformerException {
        StreamSource xml = new StreamSource(new StringReader(xmlStr));
//        StreamSource xslt = new StreamSource(new StringReader(xsltStr));
        StreamSource xslt = new StreamSource(new StringReader(xsltStrXml));

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xslt);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(xml, result);
    }

    public static final String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><data>666</data>";

    public static final String xsltStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"text\" encoding=\"utf-8\"/><xsl:template match=\"/\">\t{\"a\":\"<xsl:apply-templates select=\"data\"/>\"}</xsl:template></xsl:stylesheet>\n";

    public static final String xsltStrXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\" encoding=\"utf-8\"/><xsl:template match=\"/\"><dddd><xsl:apply-templates select=\"data\"/></dddd></xsl:template></xsl:stylesheet>";
}
