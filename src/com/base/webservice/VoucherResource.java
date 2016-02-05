/**
 * Jan Michael Balbin
 * 
 */
package com.base.webservice;

import com.base.action.Inquiry;
import com.base.action.Redemption;
import com.base.action.Topup;
import com.base.action.Void;

import org.apache.log4j.Logger;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class VoucherResource extends ServerResource
{
	private static Logger logger = Logger.getLogger(VoucherResource.class);
  
	@Get
	public String represent()
	{
		String sQueryType = "";
		String sVoucherNo = "";
		String sRefNo 	  = "";
		String sAmnt	  = "";
		int iTermNo 	  = 0;
		int iReciept 	  = 0;
		
		try
		{
			Form fParam = getQuery();
			sQueryType 	= fParam.getFirstValue("ty");
			sVoucherNo 	= fParam.getFirstValue("vn");
			sRefNo 		= fParam.getFirstValue("rn");
								
			iTermNo = Integer.parseInt(fParam.getFirstValue("tn"));
			iReciept = Integer.parseInt(fParam.getFirstValue("re"));
			
			sAmnt 		= fParam.getFirstValue("am");
      
			if (sQueryType.contentEquals("IN"))
			{
				Inquiry inquiry = new Inquiry();
				return inquiry.doInquiry(sVoucherNo, sRefNo, Integer.valueOf(iTermNo), Integer.valueOf(iReciept), sAmnt);
			}
			if (sQueryType.contentEquals("RE"))
			{
				Redemption redemption = new Redemption();
				return redemption.doRedemption(sVoucherNo, sRefNo, Integer.valueOf(iTermNo), Integer.valueOf(iReciept), sAmnt);
			}
			if (sQueryType.contentEquals("TO"))
			{
				Topup topup = new Topup();
				return topup.doTopup(sVoucherNo, sRefNo, Integer.valueOf(iTermNo), Integer.valueOf(iReciept), sAmnt);
			}
			if (sQueryType.contentEquals("VO"))
			{
				Void gcvoid = new Void();
				return gcvoid.doVoid(sVoucherNo, sRefNo);
			}
		}
		catch (Exception e)
		{
			logger.error("Error in Service Response: ", e);
			logger.error("Check exception error for details. Sending Fail to POS");
			logger.error("<response>099|Connection Failed|" + sVoucherNo + "</response>");
		}
		return "<response>099|Connection Failed|" + sVoucherNo + "</response>";
 	}
}
