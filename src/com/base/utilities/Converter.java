/**
 * Jan Michael Balbin
 * 
 */
package com.base.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Converter
{
	private static Logger logger = Logger.getLogger(Converter.class);
  
	/**
	 * 
	 * @param soapMsg
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws SOAPException
	 */
	public Document toDocument(SOAPMessage soapMsg) throws TransformerConfigurationException, TransformerException, SOAPException
	{
		Source src = soapMsg.getSOAPPart().getContent();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		
		DOMResult result = new DOMResult();
		transformer.transform(src, result);
    
		return (Document)result.getNode();
 	}
  
	/**
	 * 
	 * @param doc
	 * @return
	 * @throws InvalidCanonicalizerException
	 * @throws SOAPException
	 * @throws IOException
	 * @throws CanonicalizationException
	 */
	public SOAPMessage toSOAPMessage(Document doc) throws InvalidCanonicalizerException, SOAPException, IOException, CanonicalizationException
	{
		Init.init();
    
		Canonicalizer c14n = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
		byte[] canonicalMessage = c14n.canonicalizeSubtree(doc);
		ByteArrayInputStream in = new ByteArrayInputStream(canonicalMessage);
		MessageFactory factory = MessageFactory.newInstance();
		return factory.createMessage(null, in);
	}
  
	/**
	 * 
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public String convertDocumentToString(Document doc) throws TransformerException
	{
		TransformerFactory tf = TransformerFactory.newInstance();
    
		logger.info("Converting document to string");
    
		Transformer transformer = tf.newTransformer();
   
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();
    
		return output;
	}
  
	/**
	 * 
	 * @param xmlStr
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document convertStringToDocument(String xmlStr) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
		logger.info("Converting string to document");
    
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
		return doc;
	}
}
