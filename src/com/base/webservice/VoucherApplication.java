/**
 * Jan Michael Balbin
 * 
 */
package com.base.webservice;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class VoucherApplication extends Application
{
    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
    	
    	// Create a root router 
        Router router = new Router(getContext());

        // Defines only one route
        // Refers to voucher handler
        router.attach("/voucher", VoucherResource.class);

        return router;
    }
}
