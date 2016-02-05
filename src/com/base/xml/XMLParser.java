/**
 * Jan Michael Balbin
 * 
 */
package com.base.xml;

import com.base.utilities.Converter;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser
{
	private static Logger logger = Logger.getLogger(XMLParser.class);
  
	/**
	 * 
	 * @param xmlResponse
	 * @param sRequest
	 * @return
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ArrayList<String> parseInquiryRedemptionXML(Document xmlResponse, String sRequest) throws TransformerException, ParserConfigurationException, SAXException, IOException
	{
		ArrayList<String> aList = new ArrayList<String>();
    
		String sCDATA 			 = "";
    
		String sVoucherNo 		 = "";
		String sResponseCode 	 = "";
		String sErrorDescription = "";
		String sExpiryDate 		 = "";
		String sAmount 			 = "";
		String sBalance 		 = "";
    
		logger.info(sRequest + ": Parsing XML response...");
    
		xmlResponse.getDocumentElement().normalize();
    
		logger.info("Root element :" + xmlResponse.getDocumentElement().getNodeName());
    
		NodeList nList = xmlResponse.getElementsByTagName("soapenv:Body");
    
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
      
			logger.info("Current Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == 1)
			{
				Element eElement = (Element)nNode;
        
				sCDATA = eElement.getElementsByTagName("executeServiceReturn").item(0).getTextContent();
				if (sCDATA.isEmpty())
				{
					logger.error("CDATA not found");
				}
				else
				{         
					sCDATA = sCDATA.substring(39);
					logger.info("FINAL-CDATA : " + sCDATA);
				}
			}
		}
		Converter convert = new Converter();
		Document docCDATA = convert.convertStringToDocument(sCDATA);
    
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
    
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("omit-xml-declaration", "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    
		DOMSource source = new DOMSource(docCDATA);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
    
		docCDATA.getDocumentElement().normalize();
		logger.info("Root element :" + docCDATA.getDocumentElement().getNodeName());
    
		NodeList nList2 = docCDATA.getElementsByTagName("giftcard");
    
		logger.info("Parsing CDATA...");
		for (int temp = 0; temp < nList2.getLength(); temp++)
		{
			Node nNode = nList2.item(temp);
      
			logger.info("Current Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == 1)
			{
				Element eElement = (Element)nNode;
        
				sResponseCode = eElement.getElementsByTagName("responseCode").item(0).getTextContent();
				logger.info("[ResponseCode]: " + sResponseCode);
        
				sErrorDescription = eElement.getElementsByTagName("errorDescription").item(0).getTextContent();
				logger.info("[ErrorDescription]: " + sErrorDescription);
        
				sVoucherNo = eElement.getElementsByTagName("VoucherNo").item(0).getTextContent();
				logger.info("[VoucherNo]: " + sVoucherNo);
				
				if (sResponseCode.contentEquals("000"))
				{
					logger.info("Response: " + sErrorDescription);
          
					sExpiryDate = eElement.getElementsByTagName("ExpiryDate").item(0).getTextContent();
					logger.info("[ExpiryDate]: " + sExpiryDate);
          
					sAmount = eElement.getElementsByTagName("Amount").item(0).getTextContent();
					logger.info("[Amount]: " + sAmount);
          
					sBalance = eElement.getElementsByTagName("Balance").item(0).getTextContent();
					logger.info("[Balance]: " + sBalance);
          
					aList.add(sResponseCode);
					aList.add(sErrorDescription);
					aList.add(sVoucherNo);
					aList.add(sAmount);
					aList.add(sBalance);
					aList.add(sExpiryDate);
				}
				else
				{
					logger.info("Error Response: " + sErrorDescription);
          
					aList.add(sResponseCode);
					aList.add(sErrorDescription);
					aList.add(sVoucherNo);
				}
			}
		}
		return aList;
	}
  

	/**
	 * 
	 * @param xmlResponse
	 * @return
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ArrayList<String> parseTopupXML(Document xmlResponse) throws TransformerException, ParserConfigurationException, SAXException, IOException
	{
		ArrayList<String> aList = new ArrayList<String>();
    
		String sCDATA = "";
		String sVoucherNo 		 = "";
		String sResponseCode 	 = "";
		String sErrorDescription = "";
		String sExpiryDate 		 = "";
		String sStatus			 = "";
		String sBalance 		 = "";
    
		xmlResponse.getDocumentElement().normalize();

		logger.info("Root element :" + xmlResponse.getDocumentElement().getNodeName());
    
		NodeList nList = xmlResponse.getElementsByTagName("soapenv:Body");
    
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
      
			logger.info("Current Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == 1)
			{
				Element eElement = (Element)nNode;
        
				sCDATA = eElement.getElementsByTagName("executeServiceReturn").item(0).getTextContent();
				if (sCDATA.isEmpty())
				{
					logger.error("CDATA not found");
				}
				else
				{
					sCDATA = sCDATA.substring(39);
					logger.info("FINAL-CDATA : " + sCDATA);
				}
			}
		}
		Converter convert = new Converter();
		Document docCDATA = convert.convertStringToDocument(sCDATA);
    
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
    
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("omit-xml-declaration", "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    
		DOMSource source = new DOMSource(docCDATA);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
    
		docCDATA.getDocumentElement().normalize();
		logger.info("Root element :" + docCDATA.getDocumentElement().getNodeName());
    
		NodeList nList2 = docCDATA.getElementsByTagName("giftcard");
    
		logger.info("Parsing CDATA...");
		for (int temp = 0; temp < nList2.getLength(); temp++)
		{
			Node nNode = nList2.item(temp);
      
			logger.info("Current Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == 1)
			{
				Element eElement = (Element)nNode;
        
				sResponseCode = eElement.getElementsByTagName("responseCode").item(0).getTextContent();
				logger.info("[ResponseCode]: " + sResponseCode);
        
				sErrorDescription = eElement.getElementsByTagName("errorDescription").item(0).getTextContent();
				logger.info("[ErrorDescription]: " + sErrorDescription);
        
				sVoucherNo = eElement.getElementsByTagName("VoucherNo").item(0).getTextContent();
				logger.info("[VoucherNo]: " + sVoucherNo);
				
				if (sResponseCode.contentEquals("000"))
				{
					logger.info("Response: " + sErrorDescription);
          
					sExpiryDate = eElement.getElementsByTagName("ExpiryDate").item(0).getTextContent();
					logger.info("[ExpiryDate]: " + sExpiryDate);
          
					sStatus = eElement.getElementsByTagName("Status").item(0).getTextContent();
					logger.info("[sStatus]: " + sStatus);
          
					sBalance = eElement.getElementsByTagName("Balance").item(0).getTextContent();
					logger.info("[Balance]: " + sBalance);
          
					aList.add(sResponseCode);
					aList.add(sErrorDescription);
					aList.add(sVoucherNo);
					aList.add("0");
					aList.add(sBalance);
					aList.add(sExpiryDate);
				}
				else
				{
					logger.info("Error Response: " + sErrorDescription);
          
					aList.add(sResponseCode);
					aList.add(sErrorDescription);
					aList.add(sVoucherNo);
				}
			}
		}
		return aList;
	}
  
	/**
	 * 
	 * @param xmlResponse
	 * @return
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ArrayList<String> parseVoidXML(Document xmlResponse) throws TransformerException, ParserConfigurationException, SAXException, IOException
	{
		ArrayList<String> aList = new ArrayList<String>();
    
		String sCDATA = "";
		String sVoucherNo 		 = "";
		String sResponseCode 	 = "";
		String sErrorDescription = "";
    
		xmlResponse.getDocumentElement().normalize();
    
		logger.info("Root element :" + xmlResponse.getDocumentElement().getNodeName());
    
		NodeList nList = xmlResponse.getElementsByTagName("soapenv:Body");
    
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
      
			logger.info("Current Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == 1)
			{
				Element eElement = (Element)nNode;
				sCDATA = eElement.getElementsByTagName("executeServiceReturn").item(0).getTextContent();
				if (sCDATA.isEmpty())
				{
					logger.error("CDATA not found");
				}
				else
				{
					logger.info("CDATA found");
					logger.info("String-CDATA : " + sCDATA);
					logger.info("removing CDATA XML header");
          
					sCDATA = sCDATA.substring(39);
					logger.info("FINAL-CDATA : " + sCDATA);
				}
			}
		}	
		Converter convert = new Converter();
		Document docCDATA = convert.convertStringToDocument(sCDATA);
    
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
    
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("omit-xml-declaration", "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    
		// Printing on console
		DOMSource source = new DOMSource(docCDATA);
		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
    
		docCDATA.getDocumentElement().normalize();
		logger.info("Root element :" + docCDATA.getDocumentElement().getNodeName());
    
		NodeList nList2 = docCDATA.getElementsByTagName("giftcard");
    
		logger.info("Parsing CDATA...");
		for (int temp = 0; temp < nList2.getLength(); temp++)
		{
			Node nNode = nList2.item(temp);
      
			logger.info("Current Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == 1)
			{
				Element eElement = (Element)nNode;
        
				sResponseCode = eElement.getElementsByTagName("responseCode").item(0).getTextContent();
				logger.info("[ResponseCode]: " + sResponseCode);
        
				sErrorDescription = eElement.getElementsByTagName("errorDescription").item(0).getTextContent();
				logger.info("[ErrorDescription]: " + sErrorDescription);
        
				sVoucherNo = eElement.getElementsByTagName("VoucherNo").item(0).getTextContent();
				logger.info("[VoucherNo]: " + sVoucherNo);
       
				if (sResponseCode.contentEquals("000")) {
					logger.info("Response: " + sErrorDescription);
				} else {
					logger.info("Error Response: " + sErrorDescription);
				}
				aList.add(sResponseCode);
				aList.add(sErrorDescription);
				aList.add(sVoucherNo);
			}
		}
		return aList;
	}
	
}
