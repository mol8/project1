package com.project.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;
import com.project.util.SendMail;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.hoh.raw.api.RawSendable;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.message.ACK;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.parser.Parser;

@Controller
public class ORUController {

	private static final Logger logger = Logger.getLogger(ORUController.class);

	@ResponseBody
	@RequestMapping(value = "/ORUListener", method = RequestMethod.POST)
	public String oruController(HttpSession session, HttpServletRequest request) {
		
		String response="response";
		ORU_R01 msg = null;
		
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
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            out.append(line+"\n");
	        }
	        logger.info(out.toString());   //Prints the string content read from input stream
	        reader.close();
		
			String incomingRawMsg = out.toString();
			logger.info("Received message:\n"+incomingRawMsg);
			
			//do processing
			//generate a HL7 ORU from theReceived
			HapiContext context = new DefaultHapiContext();
			Parser parser = context.getPipeParser();
			
			
		
			msg = (ORU_R01) parser.parse(incomingRawMsg);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Error al parsear el mensaje recivido.");
			logger.error(e.getMessage());
			try {
				ack.getMSA().getMsa1_AcknowledgementCode().setValue("AE");
				ack.getMSA().getMsa3_TextMessage().setValue("Not ORU_R01");
				response = ack.encode();
					
			} catch (DataTypeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HL7Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return response;
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
					+ "/n"
					+"En el siguiente enlace podra acceder a los resultados de su prueba diagnostica, del dia: "+study.fechaString(study.getScheduledProcedureStepStartDateTime())
					+ "/n"
					+study.getUrl()
					+ "/n"
					+ "/n";
			
			SendMail sendMail = new SendMail(email, mensage, request);
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
		return response;
		
	}

}
