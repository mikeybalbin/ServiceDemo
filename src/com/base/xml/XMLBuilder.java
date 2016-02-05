/**
 * Jan Michael Balbin
 * 
 */
package com.base.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.base.environment.Environment;
import com.base.environment.EnvironmentConstants;

public class XMLBuilder
{
	private static Logger logger = Logger.getLogger(XMLBuilder.class);
	
	private static final String XMLNS_ATT = Environment.getProperty(EnvironmentConstants.XMLNS_ATT);
  
	/**
	 * 
	 * @param sVoucherNo
	 * @param sRefNo
	 * @param iTermNo
	 * @param iReciept
	 * @param sAmnt
	 * @param iStoreNo
	 * @param sDate
	 * @param sType
	 * @return
	 */
	public Document buildInquiryRedeemXML(String sVoucherNo, String sRefNo, Integer iTermNo, Integer iReciept, String sAmnt, Integer iStoreNo, String sDate, String sType)
	{
		Document doc = null;
		StringBuilder sb = new StringBuilder();
		logger.info("Building XML request: " + sType + " : " + sVoucherNo);
    
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("soapenv:Envelope");
			doc.appendChild(rootElement);
      
			Attr rootAtt = doc.createAttribute("xmlns:soapenv");
			rootAtt.setValue("http://schemas.xmlsoap.org/soap/envelope/");
			rootElement.setAttributeNode(rootAtt);
      
			rootElement.setAttribute("xmlns:web", XMLNS_ATT);
      
			Element header = doc.createElement("soapenv:Header");
			rootElement.appendChild(header);
      
			Element body = doc.createElement("soapenv:Body");
			rootElement.appendChild(body);
      
			Element webexecute = doc.createElement("web:executeService");
			body.appendChild(webexecute);
      
			Element webopcode = doc.createElement("web:opcode");
			webopcode.appendChild(doc.createTextNode("UpdateGCRedeem"));
			webexecute.appendChild(webopcode);
      
			Element webinputXML = doc.createElement("web:inputXML");
			webexecute.appendChild(webinputXML);
      
			sb.append("<giftcard>");
			sb.append("<VoucherNo>");
			sb.append(sVoucherNo);
			sb.append("</VoucherNo><Type>");
			sb.append(sType);
			sb.append("</Type><StoreCode>");
			sb.append(iStoreNo);
			sb.append("</StoreCode><Date>");
			sb.append(sDate);
			sb.append("</Date><TerminalNo>");
			sb.append(iTermNo);
			sb.append("</TerminalNo><RefNum>");
			sb.append(sRefNo);
			sb.append("</RefNum><Amount>");
			sb.append(sAmnt);
			sb.append("</Amount></giftcard>");
      
			System.out.println("Deserialized request body: " + sb.toString());
			logger.info("Deserialized request body: " + sb.toString());
			
			webinputXML.appendChild(doc.createTextNode(sb.toString()));
			webexecute.appendChild(webinputXML);
      
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
      
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      
			doc.setXmlStandalone(true);
			
			// Prints the request on console for debugging
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
      
			return doc;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
			logger.error("Error in building XML: " + pce.toString());
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
			logger.error("Error in building XML: " + tfe.toString());
		}
		return doc;
	}
  
	/**
	 * 
	 * @param sVoucherNo
	 * @param sRefNo
	 * @param iTermNo
	 * @param iReciept
	 * @param sAmnt
	 * @param iStoreNo
	 * @param sDate
	 * @return
	 */
	public Document buildTopupXML(String sVoucherNo, String sRefNo, Integer iTermNo, Integer iReciept, String sAmnt, Integer iStoreNo, String sDate)
	{
		Document doc = null;
		StringBuilder sb = new StringBuilder();
		logger.info("Building XML Topup request: " + sVoucherNo);
   
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("soapenv:Envelope");
			doc.appendChild(rootElement);
      
			Attr rootAtt = doc.createAttribute("xmlns:soapenv");
			rootAtt.setValue("http://schemas.xmlsoap.org/soap/envelope/");
			rootElement.setAttributeNode(rootAtt);
      
			rootElement.setAttribute("xmlns:web", XMLNS_ATT);
      
			Element header = doc.createElement("soapenv:Header");
			rootElement.appendChild(header);
      
			Element body = doc.createElement("soapenv:Body");
			rootElement.appendChild(body);
      
			Element webexecute = doc.createElement("web:executeService");
			body.appendChild(webexecute);
      
			Element webopcode = doc.createElement("web:opcode");
			webopcode.appendChild(doc.createTextNode("UpdateGCTopUp"));
			webexecute.appendChild(webopcode);
      

			Element webinputXML = doc.createElement("web:inputXML");
			webexecute.appendChild(webinputXML);
      
			sb.append("<giftcard>");
			sb.append("<VoucherNo>");
			sb.append(sVoucherNo);
			sb.append("</VoucherNo><StoreCode>");
			sb.append(iStoreNo);
			sb.append("</StoreCode><Date>");
			sb.append(sDate);
			sb.append("</Date><TerminalNo>");
			sb.append(iTermNo);
			sb.append("</TerminalNo><RefNum>");
			sb.append(sRefNo);
			sb.append("</RefNum><Amount>");
			sb.append(sAmnt);
			sb.append("</Amount></giftcard>");
      
			System.out.println("Deserialized request body: " + sb.toString());
			logger.info("Deserialized request body: " + sb.toString());
      
			webinputXML.appendChild(doc.createTextNode(sb.toString()));
			webexecute.appendChild(webinputXML);
      
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
      
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      
			doc.setXmlStandalone(true);
		      
			// Prints the request
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
			return doc;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
			logger.error("Error in building XML: " + pce.toString());
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
			logger.error("Error in building XML: " + tfe.toString());
		}
		return doc;
	}
  
	/**
	 * 
	 * @param sVoucherNo
	 * @param sRefNo
	 * @return
	 */
	public Document buildVoidXML(String sVoucherNo, String sRefNo)
	{
		Document doc = null;
		StringBuilder sb = new StringBuilder();
		logger.info("Building XML Void request: " + sVoucherNo);
    
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("soapenv:Envelope");
			doc.appendChild(rootElement);
      
			Attr rootAtt = doc.createAttribute("xmlns:soapenv");
			rootAtt.setValue("http://schemas.xmlsoap.org/soap/envelope/");
			rootElement.setAttributeNode(rootAtt);
      
			rootElement.setAttribute("xmlns:web", XMLNS_ATT);
      
			Element header = doc.createElement("soapenv:Header");
			rootElement.appendChild(header);
      
			Element body = doc.createElement("soapenv:Body");
			rootElement.appendChild(body);
      
			Element webexecute = doc.createElement("web:executeService");
			body.appendChild(webexecute);
      
			Element webopcode = doc.createElement("web:opcode");
			webopcode.appendChild(doc.createTextNode("UpdateGCVoid"));
			webexecute.appendChild(webopcode);
      
			Element webinputXML = doc.createElement("web:inputXML");
			webexecute.appendChild(webinputXML);
      
			sb.append("<giftcard>");
			sb.append("<VoucherNo>");
			sb.append(sVoucherNo);
			sb.append("</VoucherNo><RefNum>");
			sb.append(sRefNo);
			sb.append("</RefNum></giftcard>");
      
			System.out.println("Deserialized request body: " + sb.toString());
			logger.info("Deserialized request body: " + sb.toString());
      
			webinputXML.appendChild(doc.createTextNode(sb.toString()));
			webexecute.appendChild(webinputXML);
      
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
      
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      
			doc.setXmlStandalone(true);
      
			// Prints the request
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
      
			return doc;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
			logger.error("Error in building XML: " + pce.toString());
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
			logger.error("Error in building XML: " + tfe.toString());
		}
		return doc;
	}
	
	
}
