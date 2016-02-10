/**
 * Jan Michael Balbin
 * 
 */
package com.base.soap;

import com.base.certificate.Certificate;
import com.base.environment.Environment;
import com.base.environment.EnvironmentConstants;
import com.base.utilities.Converter;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class SoapHandler
{
	private static Logger logger = Logger.getLogger(SoapHandler.class);
	private static final String URL_SERVICE = Environment.getProperty(EnvironmentConstants.URL_SERVICE);
  
	/**
	 * 
	 * @param docRequest
	 * @return
	 * @throws Exception
	 */
	public Document sendRequest(Document docRequest) throws Exception
	{
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
    
		String url = URL_SERVICE;
		logger.info("Connecting to service: " + URL_SERVICE);
    
		logger.info("Checking Certificates..");
		Certificate cert = new Certificate();
		cert.doTrustToCertificates();
    
		logger.info("Converting Document Request to SOAP..");
		Converter converter = new Converter();
		SOAPMessage soapTmp = converter.toSOAPMessage(docRequest);
    
		logger.info("Setting SOAP Header..");
		MimeHeaders headers = soapTmp.getMimeHeaders();
		headers.addHeader("SOAPAction", URL_SERVICE);
    
		soapTmp.saveChanges();
    
		logger.info("Sending SOAP Request..");
		SOAPMessage soapResponse = soapConnection.call(soapTmp, url);

		System.out.print("SOAP Response\n");
		soapResponse.writeTo(System.out);
		
		logger.info("RAW SOAP Response: "+soapResponse.toString());
    
		logger.info("Converting SOAP Response to Document..");
		Document docResponse = converter.toDocument(soapResponse);
    
		soapConnection.close();
    
		return docResponse;
	}
}
