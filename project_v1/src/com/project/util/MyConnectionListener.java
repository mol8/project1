package com.project.util;

import org.apache.log4j.Logger;

import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.ConnectionListener;

public class MyConnectionListener implements ConnectionListener{
	
	private static final Logger logger = Logger.getLogger(MyConnectionListener.class);

	@Override
	public void connectionReceived(Connection c) {
		System.out.println("New connection received: "+c.getRemoteAddress().toString());
		
	}

	@Override
	public void connectionDiscarded(Connection c) {
		System.out.println("Lost connection from: "+c.getRemoteAddress().toString());
		
	}

}
