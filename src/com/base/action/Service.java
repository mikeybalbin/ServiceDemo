/**
 * Jan Michael Balbin
 * 
 */
package com.base.action;

import com.base.webservice.VoucherApplication;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class Service
{
	public static void doService()
	{
		try
		{
			Component component = new Component();      
			component.getServers().add(Protocol.HTTP, 8182);
			component.getDefaultHost().attach("/gc/servlet", new VoucherApplication());
			component.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
