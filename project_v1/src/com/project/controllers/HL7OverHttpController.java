package com.project.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;
import com.project.util.SendMail;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.hoh.api.IAuthorizationServerCallback;
import ca.uhn.hl7v2.hoh.api.IMessageHandler;
import ca.uhn.hl7v2.hoh.api.IReceivable;
import ca.uhn.hl7v2.hoh.api.IResponseSendable;
import ca.uhn.hl7v2.hoh.api.MessageMetadataKeys;
import ca.uhn.hl7v2.hoh.api.MessageProcessingException;
import ca.uhn.hl7v2.hoh.auth.SingleCredentialServerCallback;
import ca.uhn.hl7v2.hoh.raw.api.RawSendable;
import ca.uhn.hl7v2.hoh.raw.server.HohRawServlet;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.message.ACK;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.parser.Parser;

public class HL7OverHttpController extends HohRawServlet{
	
	private static final Logger logger = Logger.getLogger(HL7OverHttpController.class);
	//private static String path;
	/*
	 * constructor
	 */
	public HL7OverHttpController(){
		logger.info("Iniciamos HL7OverHttp");
		 //path= getServletContext().getRealPath("/");
		//ServletContext servletContext = this.getServletContext();
		//String path = servletContext.getRealPath("/");
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
		//IAuthorizationServerCallback callback = new SingleCredentialServerCallback("test", "test");
		//setAuthorizationCallback(callback);
	}
	
	/*
	 * IMessageHandler defines the interface for the class which receives 
	 * and processes messages which come in
	 */
	
	private static class MessageHandler implements IMessageHandler<String>{
		

		@Override
		public IResponseSendable<String> messageReceived(IReceivable<String> theReceived) throws MessageProcessingException {
			
			String response="response";
			
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

			
			String incomingRawMsg = theReceived.getMessage();
			logger.info("Received message:\n"+incomingRawMsg);
			
			//do processing
			//generate a HL7 ORU from theReceived
			HapiContext context = new DefaultHapiContext();
			Parser parser = context.getPipeParser();
			
			ORU_R01 msg = null;
			
			try{
				msg = (ORU_R01) parser.parse(incomingRawMsg);
			}catch (Exception e) {
				// TODO: handle exception
				logger.error("Error al parsear el mensaje recivido.");
				logger.error(e.getMessage());
				try {
					ack.getMSA().getMsa1_AcknowledgementCode().setValue("AE");
					ack.getMSA().getMsa3_TextMessage().setValue("Not ORU_R01");
						
				} catch (DataTypeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return new RawSendable(response);
			}
			
			//obtenemos el controlID del mensaje enviado.
			String controlID = msg.getMSH().getMessageControlID().getValue();
			logger.info("ControlID del mansaje: "+controlID);
			
			//obtenemos el UID del estudio
			ORU_R01_ORDER_OBSERVATION orderObservation = msg.getPATIENT_RESULT().getORDER_OBSERVATION();
			
			OBR obr = orderObservation.getOBR();
			String studyUID = obr.getObr2_PlacerOrderNumber().getEi3_UniversalID().getValue();
			logger.info("studyUID: "+studyUID);
			
			ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(0);
			
			OBX obx = observation.getOBX();
			String accessURL = null;
			try {
				accessURL = obx.getObx5_ObservationValue(0).getData().encode();
			} catch (HL7Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("accessURL: "+accessURL);
			
			if(RegistryDAO.getStudyDAO().existsStudyByUID(studyUID)){
				//si existe el estudio que estamos buscando
				//marcamos el studio con studyUID como completado
				Study study = RegistryDAO.getStudyDAO().getStudyByDicomStudyInstanceUid(studyUID);
				study.setStatus("FINALIZADO");
				study.setUrl(accessURL);
				RegistryDAO.getStudyDAO().modificaStudy(study);
				
				//enviamos correo al paciente con los datos de acceso al estudio.
				Patient patient = study.getPatient();
				Users user = patient.getUsers();
				
				String email = user.getEmail();
				
				String mensage = "Estimado "+user.getName()+" "+user.getSurename()+","
						+ "\n"
						+"En el siguiente enlace podra acceder a los resultados de su prueba diagnostica, del dia: "+study.fechaString(study.getScheduledProcedureStepStartDateTime())
						+ "\n"
						+study.getUrl()
						+ "\n"
						+ "\n";
				
				SendMail sendMail = new SendMail(email, mensage);
				sendMail.run();
				
				//ACK AA - correcto
				try {
					ack.getMSA().getMsa1_AcknowledgementCode().setValue("AA");
					ack.getMSH().getMessageControlID().setValue(controlID);
					ack.getMSA().getMsa2_MessageControlID().setValue(controlID);
					response = ack.encode();
					
				} catch (DataTypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HL7Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else{
				//ACK AE - estudio no encontrado en la aplicacion.
				try {
					ack.getMSA().getMsa1_AcknowledgementCode().setValue("AE");
					ack.getMSH().getMessageControlID().setValue(controlID);
					ack.getMSA().getMsa2_MessageControlID().setValue(controlID);
					ack.getMSA().getMsa3_TextMessage().setValue("UID not find in Data Base");
					response = ack.encode();
					
				} catch (DataTypeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HL7Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			logger.info(response);
			return new RawSendable(response);
		}
		
	}

}
