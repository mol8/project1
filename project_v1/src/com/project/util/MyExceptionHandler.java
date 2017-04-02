package com.project.util;

import java.util.Map;

import org.apache.log4j.Logger;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler;

public class MyExceptionHandler implements ReceivingApplicationExceptionHandler {
	
	private static final Logger logger = Logger.getLogger(MyExceptionHandler.class);

	@Override
	public String processException(String incomingMessage, Map<String, Object> incomingMetadata, String outgoingMessage,
			Exception e) throws HL7Exception {
		/*
		 * Here you can do any processing you like. If you want to change the response (NAK) message which will be returned you may do so,
		 * or just return the NAK which HAPI already created (ttheOutgoingMessage)
		 */
		logger.error(outgoingMessage);
		return outgoingMessage;
	}

}
