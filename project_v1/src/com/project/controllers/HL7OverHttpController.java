package com.project.controllers;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.hoh.api.IAuthorizationServerCallback;
import ca.uhn.hl7v2.hoh.api.IMessageHandler;
import ca.uhn.hl7v2.hoh.api.IReceivable;
import ca.uhn.hl7v2.hoh.api.IResponseSendable;
import ca.uhn.hl7v2.hoh.api.MessageProcessingException;
import ca.uhn.hl7v2.hoh.auth.SingleCredentialServerCallback;
import ca.uhn.hl7v2.hoh.raw.api.RawSendable;
import ca.uhn.hl7v2.hoh.raw.server.HohRawServlet;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.message.ACK;

public class HL7OverHttpController extends HohRawServlet{
	
	private static final Logger logger = Logger.getLogger(HL7OverHttpController.class);
	/*
	 * constructor
	 */
	public HL7OverHttpController(){
		logger.info("Iniciamos HL7OverHttp");
		/*
		 * The servlet must be provided an implementation
		 * of IMessageHandler<String>, such as the one which
		 * is nested below
		 */
		setMessageHandler(new MessageHandler());
		
		/*
		 * Optionally, if we want to verify HTTP authentication,
		 * we can specify an authorization callback
		 */
		IAuthorizationServerCallback callback = new SingleCredentialServerCallback("test", "test");
		setAuthorizationCallback(callback);
	}
	
	/*
	 * IMessageHandler defines the interface for the class which receives 
	 * and processes messages which come in
	 */
	private static class MessageHandler implements IMessageHandler<String>{

		@Override
		public IResponseSendable<String> messageReceived(IReceivable<String> theReceived) throws MessageProcessingException {
			
			String response="response";
			
			String incomingRawMsg = theReceived.getMessage();
			logger.info("Received message:\n"+incomingRawMsg);
			
			//do processing
			//generate a HL7 ORU from theReceived
			
			//send ACK
			ACK ack = new ACK();
			try {
				ack.initQuickstart("ACK", null, null);
			} catch (HL7Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				ack.getMSA().getMsa1_AcknowledgementCode().setValue("AA");
				//ack.getMSA().getMsa2_MessageControlID().setValue(message.getMSH().getMessageControlID().getValue());
				response = ack.encode();
				
			} catch (HL7Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			// TODO Auto-generated method stub
			return new RawSendable(response);
		}
		
	}

}
