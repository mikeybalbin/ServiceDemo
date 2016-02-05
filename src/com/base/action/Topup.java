/**
 * Jan Michael Balbin
 * 
 */
package com.base.action;

import com.base.connection.DAOFactory;
import com.base.dataaccess.DataExtractDaoI;
import com.base.soap.SoapHandler;
import com.base.xml.XMLBuilder;
import com.base.xml.XMLParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class Topup
{
	private static Logger logger = Logger.getLogger("serviceLogger");
  
	/**
	 * 
	 * @param sVoucherNo
	 * @param sRefNo
	 * @param iTermNo
	 * @param iReciept
	 * @param sAmnt
	 * @return
	 * @throws Exception
	 */
	public String doTopup(String sVoucherNo, String sRefNo, Integer iTermNo, Integer iReciept, String sAmnt) throws Exception
	{
		StringBuilder sb = new StringBuilder();
    
		ArrayList<String> aList = new ArrayList<String>();
    
		String sDate = "";
		int iStoreNo = 0;
    
		logger.info("Mode type Topup on voucher: " + sVoucherNo);
   
		Date dSystemDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		DataExtractDaoI dataDao = daoFactory.getDataExtractDaoI();
    
		iStoreNo = dataDao.getStoreNumber();
		sDate = dateFormat.format(dSystemDate).toString();
    
		logger.info("Store Number: " + iStoreNo);
		logger.info("Date today: " + sDate);
    
		XMLBuilder xmlBuild = new XMLBuilder();
		Document xmlRequest = xmlBuild.buildTopupXML(sVoucherNo, sRefNo, iTermNo, iReciept, sAmnt, Integer.valueOf(iStoreNo), sDate);
    

		SoapHandler doRequest = new SoapHandler();
		Document xmlResponse = doRequest.sendRequest(xmlRequest);
    
		XMLParser xmlParse = new XMLParser();
		aList = xmlParse.parseTopupXML(xmlResponse);
    

		logger.info("Posting response to POS");
		if (aList.get(0).contentEquals("000"))
		{
			sb.append("<response>");
			sb.append(aList.get(0).toString());
			sb.append("|");
			sb.append(aList.get(1).toString());
			sb.append("|");
			sb.append(aList.get(2).toString());
			sb.append("|");
			sb.append(aList.get(3).toString());
			sb.append("|");
			sb.append(aList.get(4).toString());
			sb.append("|");
			sb.append(aList.get(5).toString());
			sb.append("</response>");
		}
		else
		{
			sb.append("<response>");
			sb.append(aList.get(0).toString());
			sb.append("|");
			sb.append(aList.get(1).toString());
			sb.append("|");
			sb.append(aList.get(2).toString());
			sb.append("</response>");
		}
		
		logger.info(sb.toString());
		
		return sb.toString();
	}
}
