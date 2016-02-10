/**
 * Jan Michael Balbin
 * 
 */
package com.base.action;

import com.base.soap.SoapHandler;
import com.base.xml.XMLBuilder;
import com.base.xml.XMLParser;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class Void
{
	private static Logger logger = Logger.getLogger("serviceLogger");
  
	/**
	 * 
	 * @param sVoucherNo
	 * @param sRefNo
	 * @return
	 * @throws Exception
	 */
	public String doVoid(String sVoucherNo, String sRefNo) throws Exception
	{
		StringBuilder sb = new StringBuilder();
    
		ArrayList<String> aList = new ArrayList<String>();
    
		logger.info("Mode type Void on voucher: " + sVoucherNo);
		
		XMLBuilder xmlBuild = new XMLBuilder();
		Document xmlRequest = xmlBuild.buildVoidXML(sVoucherNo, sRefNo);
    
		SoapHandler doRequest = new SoapHandler();
		Document xmlResponse = doRequest.sendRequest(xmlRequest);
    
		XMLParser xmlParse = new XMLParser();
		aList = xmlParse.parseVoidXML(xmlResponse);
    
		logger.info("Posting response to POS");
    
		sb.append("<response>");
		sb.append(aList.get(0).toString());
		sb.append("|");
		sb.append(aList.get(1).toString());
		sb.append("|");
		sb.append(aList.get(2).toString());
		sb.append("</response>");
    
		logger.info(sb.toString());
		
		return sb.toString();
		
	}
}
