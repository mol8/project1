package com.project.util;

import java.io.IOException;


import org.apache.log4j.Logger;

import com.project.controllers.entradaController;
import com.project.controllers.loginController;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ORM_O01;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;

public class sendORM extends Thread{
	
	private static final Logger logger = Logger.getLogger(sendORM.class);
	
	private int localPort;
	private boolean useTLS;
	
	private int remotePort;
	private String remoteIP;
	
	private ORM_O01 orm_message;
	
	
	public sendORM(int localPort, boolean useTLS, int remotePort, String remoteIP, ORM_O01 ormr_message) {
		super();
		this.localPort = localPort;
		this.useTLS = useTLS;
		this.remotePort = remotePort;
		this.remoteIP = remoteIP;
		this.orm_message = ormr_message;
	}
	
	public void run(){
		try {
			/*
			 * Before we can send, let's create a server to listen for incoming messages.
			 * The following section of code establishes a server listening on port 1011 for
			 * new connections, and then "handles" them by
			 */
			logger.info("iniciamos envio");
			//you can not open a port under 1024 if you don't have root access permissions 
			HapiContext context = new DefaultHapiContext();
			HL7Service server = context.newServer(localPort, useTLS);
			
			/*
			 * The server may have any number of applications object registered to handle
			 * messages. We are going to create an applications to listen to ACK
			 */
			ReceivingApplication handlerACK = new ACKReceiverApplication();
			server.registerApplication("ACK",null,handlerACK);
			
			/*
			 * If you want to be notified any time a new connection comes in or is lost,
			 * you might also want to register a connection listener.
			 */
			server.registerConnectionListener(new MyConnectionListener());
			
			/*
			 * If you want to be notified any processing failures when receiving, processing,
			 * or responding to messages with the server, you can also register an exception
			 * handler.
			 */
			server.setExceptionHandler(new MyExceptionHandler());
			
			//server starts for listening incoming messages
			logger.info("Server starts");
			server.startAndWait();
			
			while(!server.isRunning()){
				logger.info("Server is not running yet");
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
			logger.info("Server running");
			
			//Remember we created our HAPI Context above like so:
			//HapiContext context = new DefaultHapiContext();
			
			//Prepare the outgoing message
			Message message = orm_message.getMessage();
			
			//A connection object represents a socket attached to an HL7 server
			logger.info("IP envio: "+remoteIP+" Puerto:"+remotePort );
			Connection connection_dcm = context.newClient(remoteIP, remotePort, useTLS);
			
			//The initiator is used to transmit unsolicited messages
			logger.info("Iniciamos el initiator");
			Initiator initiator_dcm = connection_dcm.getInitiator();
			logger.info("enviamos el mensaje y esperamos respuesta");
			Message response_dcm = initiator_dcm.sendAndReceive(message);
			logger.info(response_dcm.toString());
			
			Parser p = context.getPipeParser();
			String responseString_dcm = p.encode(response_dcm);
			logger.info(responseString_dcm);
			
			/*
			 * Close the connection you are done with it. If you are designing a system which will continuously send out messages, you may want to 
			 * consider not closing the connection until you have no more messages to send out. This is more efficient, as most (if not all) 
			 * HL7 receiving applications  are capable of receiving lots of messages in a row over the same connection, even with a long delay 
			 * between messages.
			 */
			connection_dcm.close();
			
			//Stop the receiving server and client
			server.stopAndWait();
			/*		
			while(!server.isRunning()){
				logger.info("Server is still running");
				server.stop();
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}				
			logger.info("Server stoped");	
			*/
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error al enviar el orm");
		}
	}

	public void send() throws HL7Exception, LLPException, IOException, InterruptedException{
			
		/*
		 * Before we can send, let's create a server to listen for incoming messages.
		 * The following section of code establishes a server listening on port 1011 for
		 * new connections, and then "handles" them by
		 */
		logger.info("iniciamos envio");
		//you can not open a port under 1024 if you don't have root access permissions 
		HapiContext context = new DefaultHapiContext();
		HL7Service server = context.newServer(localPort, useTLS);
		
		/*
		 * The server may have any number of applications object registered to handle
		 * messages. We are going to create an applications to listen to ACK
		 */
		ReceivingApplication handlerACK = new ACKReceiverApplication();
		server.registerApplication("ACK",null,handlerACK);
		
		/*
		 * If you want to be notified any time a new connection comes in or is lost,
		 * you might also want to register a connection listener.
		 */
		server.registerConnectionListener(new MyConnectionListener());
		
		/*
		 * If you want to be notified any processing failures when receiving, processing,
		 * or responding to messages with the server, you can also register an exception
		 * handler.
		 */
		server.setExceptionHandler(new MyExceptionHandler());
		
		//server starts for listening incoming messages
		logger.info("Server starts");
		server.startAndWait();
		
		while(!server.isRunning()){
			logger.info("Server is not running yet");
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
		logger.info("Server running");
		
		//Remember we created our HAPI Context above like so:
		//HapiContext context = new DefaultHapiContext();
		
		//Prepare the outgoing message
		Message message = orm_message.getMessage();
		
		//A connection object represents a socket attached to an HL7 server
		logger.info("IP envio: "+remoteIP+" Puerto:"+remotePort );
		Connection connection_dcm = context.newClient(remoteIP, remotePort, useTLS);
		
		//The initiator is used to transmit unsolicited messages
		logger.info("Iniciamos el initiator");
		Initiator initiator_dcm = connection_dcm.getInitiator();
		logger.info("enviamos el mensaje y esperamos respuesta");
		Message response_dcm = initiator_dcm.sendAndReceive(message);
		logger.info(response_dcm.toString());
		
		Parser p = context.getPipeParser();
		String responseString_dcm = p.encode(response_dcm);
		logger.info(responseString_dcm);
		
		/*
		 * Close the connection you are done with it. If you are designing a system which will continuously send out messages, you may want to 
		 * consider not closing the connection until you have no more messages to send out. This is more efficient, as most (if not all) 
		 * HL7 receiving applications  are capable of receiving lots of messages in a row over the same connection, even with a long delay 
		 * between messages.
		 */
		connection_dcm.close();
		
		//Stop the receiving server and client
		server.stopAndWait();
		/*		
		while(!server.isRunning()){
			logger.info("Server is still running");
			server.stop();
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}				
		logger.info("Server stoped");	
		*/
	}
}
