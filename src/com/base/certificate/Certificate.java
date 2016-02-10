/**
 * Jan Michael Balbin
 * 
 */
package com.base.certificate;

import com.sun.net.ssl.internal.ssl.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;

public class Certificate
{
  private static Logger logger = Logger.getLogger(Certificate.class);
  
  public void doTrustToCertificates() throws Exception
  {
    Security.addProvider(new Provider());
    TrustManager[] trustAllCerts = {
    		new X509TrustManager()
    		{
    			public X509Certificate[] getAcceptedIssuers()
    			{
    				return null;
    			}
        
    			public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException
    			{}
        
    			public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException
    			{}
    		} 
    };
    
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    HostnameVerifier hv = new HostnameVerifier()
    {
    	public boolean verify(String urlHostName, SSLSession session)
    	{
    		if (!urlHostName.equalsIgnoreCase(session.getPeerHost()))
    		{
    			Certificate.logger.warn("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
    			System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
    		}
    		return true;
    	}
    };
    HttpsURLConnection.setDefaultHostnameVerifier(hv);
  
  }
  
  
}
