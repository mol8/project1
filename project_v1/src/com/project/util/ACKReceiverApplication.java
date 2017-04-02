package com.project.util;

import java.util.Map;

import org.apache.log4j.Logger;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

public class ACKReceiverApplication implements ReceivingApplication {
	
	private static final Logger logger = Logger.getLogger(ACKReceiverApplication.class);

	@Override
	public Message processMessage(Message theMessage, Map<String, Object> theMetadata)
			throws ReceivingApplicationException, HL7Exception {
		
		String encodeMessage = new DefaultHapiContext().getPipeParser().encode(theMessage);
		//System.out.println("Received ACK: \n" + encodeMessage +"\n\n");
		logger.info(encodeMessage);
		return null;
	}

	@Override
	public boolean canProcess(Message theMessage) {
		// TODO Auto-generated method stub
		return false;
	}

}
