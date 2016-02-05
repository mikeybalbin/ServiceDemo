/**
 * Jan Michael Balbin
 * 
 */
package com.base.action;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class VoucherMain {

	private static Logger logger = Logger.getLogger(VoucherMain.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String log4jConfPath = "/home/tplinux/gcservice/env/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
		//BasicConfigurator.configure();
		
		logger.info("Starting Client Services");
		Service.doService();
	}
	
}
