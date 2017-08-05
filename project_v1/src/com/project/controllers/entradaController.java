package com.project.controllers;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.naming.GenericNamingResourcesFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Equipment;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;
import com.project.util.PasswordGenerator;
import com.project.util.SendFile;
import com.project.util.generateORM;
import com.project.util.sendORM;
import com.project.util.writeXML;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.llp.LLPException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class entradaController {

	private static final Logger logger = Logger.getLogger(entradaController.class);

	public enum Estado {
		PROGRAMADO, ENTRADA, FINALIZADO, CANCELADO
	}

	@RequestMapping(value = "/entrada/{idstudy}", method = RequestMethod.GET)
	public ModelAndView entrada(@PathVariable("idstudy") String idstudy, HttpSession session) throws IOException {
		logger.debug("/entrada/" + idstudy);
		logger.info("Generamos el ORM y lo enviamos a la worklist");

		String path = session.getServletContext().getRealPath("/") + "//WEB-INF//resources//";
		Properties prop = new Properties();
		InputStream input = null;

		input = new FileInputStream(path + File.separator + "config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		logger.info(prop.getProperty("incomingPort"));
		logger.info(prop.getProperty("useTLS"));
		logger.info(prop.getProperty("outgoingPort"));
		logger.info(prop.getProperty("sendingIP"));

		int incomingPort = Integer.parseInt(prop.getProperty("incomingPort"));
		boolean useTLS = Boolean.parseBoolean(prop.getProperty("useTLS"));
		int outgoingPort = Integer.parseInt(prop.getProperty("outgoingPort"));
		String sendingIP = prop.getProperty("sendingIP");

		// obtenemos los datos del paciente y estudio seleccionados idstudy
		Study study = RegistryDAO.getStudyDAO().getStudyByID(Integer.parseInt(idstudy));

		// Con el id de este estudio recuperaremos:
		// paciente
		Patient patient = study.getPatient();
		// Patient patient =
		// RegistryDAO.getPatientDAO().getPatientByID(study.getPatient().getIdpatient());
		// usuario
		Users user = patient.getUsers();
		// modalidad
		Equipment equipment = study.getEquipment();

		logger.info("Recuperado - Estudio con id:" + study.getIdstudy());
		logger.info("Recuperado - Paciente con id:" + patient.getIdpatient());
		logger.info("Recuperado - User con id:" + user.getIduser());
		logger.info("Recuperado - equipment con id:" + equipment.getIdequipment());

		// para la generacion del ORM utilizamos la libreria HAPI
		// se han creado una serie de utilidades para la creacion del ORM y
		// envio

		generateORM generate_ORM = new generateORM();
		generate_ORM.setPID_patientID(patient.getIdpatient());
		generate_ORM.setPID_givenName(user.getName());
		generate_ORM.setPID_familyName(user.getSurename());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dob = sdf.format(patient.getDateOfBirth());
		generate_ORM.setPID_dateOfBirth(dob);
		generate_ORM.setPID_sex(patient.getSex());

		generate_ORM.setPV1_facility(study.getRequestingService());
		generate_ORM.setPV1_attendingDoctorFamilyName(study.getReferringPhysician());
		generate_ORM.setPV1_attendingDoctorGivenName(study.getReferringPhysician());
		generate_ORM.setPV1_attendingDoctorID(study.getReferringPhysician());

		generate_ORM.setORC_orderControl("NW");
		generate_ORM.setORC_namespaceID(study.getIssuer());
		generate_ORM.setORC_universalID(study.getDicomStudyInstanceUid());

		// Accession Number
		String accessionNumber = PasswordGenerator.getPassword(8);
		generate_ORM.setOBR2_placeOrderNumber_entityIdentifier(accessionNumber);
		generate_ORM.setOBR2_placeOrderNumber_namespaceID(study.getDicomStudyInstanceUid());
		generate_ORM.setOBR2_placeOrderNumber_universalID(study.getIssuer());

		generate_ORM.setOBR4_universalServiceIdentifier_identifier(study.getRequestedProcedureCode());
		generate_ORM.setOBR4_universalServiceIdentifier_text(study.getRequestedProcedureDescription());
		generate_ORM.setOBR4_universalServiceIdentifier_nameOfCodingSystem(study.getRequestedProcedureDescription());
		generate_ORM.setOBR4_universalServiceIdentifier_alternateIdentifier(study.getRequestedProcedureCode());

		generate_ORM.setOBR18_placerField1(equipment.getAeTitle());
		generate_ORM.setOBR19_placerFiel2(equipment.getModality());
		generate_ORM.setOBR24_diagnosticServSectID(equipment.getModality());

		try {
			// leemos los datos de configuracion de envio del fichero de
			// configuracion
			sendORM send_ORM = new sendORM(incomingPort, useTLS, outgoingPort, sendingIP, generate_ORM.orm());
			send_ORM.start();
		} catch (HL7Exception | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		// cambiamos el estado delestudio a ENTRADA
		study.setStatus(Estado.ENTRADA.toString());
		study.setAccessionNumber(accessionNumber);
		boolean message = RegistryDAO.getStudyDAO().modificaStudy(study);
		logger.info("Cambio de estado de estudio " + study.getIdstudy() + " :" + message);

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.debug("Username: " + username);

		ModelAndView mav = new ModelAndView("home");

		mav.addObject("username", username);

		// obtenemos un listado de todos los estudios citados
		logger.info("Mostramos listado de estudios citados.");
		List<Study> todayStudies = RegistryDAO.getStudyDAO().getTodayStudies();
		logger.info("Estudios encontrados: " + todayStudies.size());
		for (Study study_aux : todayStudies) {
			logger.info(study_aux.getIdstudy() + " " + study_aux.getPatient().getIdpatient());
		}
		mav.addObject("todayStudies", todayStudies);

		return mav;
	}

	@RequestMapping(value = "/cancel/{idstudy}", method = RequestMethod.GET)
	public ModelAndView cancel(@PathVariable("idstudy") String idstudy, HttpSession session) throws IOException {

		logger.debug("/cancel/" + idstudy);
		logger.info("Generamos creamos el XML con los datos para el mensaje de cancelacion");

		String path = session.getServletContext().getRealPath("/") + "//WEB-INF//resources//";
		Properties prop = new Properties();
		InputStream input = null;
		
		String error="";

		input = new FileInputStream(path + File.separator + "config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		logger.info(prop.getProperty("mirthIP"));
		logger.info(prop.getProperty("mirthPort"));

		String mirthIP = prop.getProperty("mirthIP");
		int mirthPort = Integer.parseInt(prop.getProperty("mirthPort"));

		// obtenemos los datos del paciente y estudio seleccionados idstudy
		Study study = RegistryDAO.getStudyDAO().getStudyByID(Integer.parseInt(idstudy));
		// Con el id de este estudio recuperaremos:
		// paciente
		Patient patient = study.getPatient();
		// Patient patient =
		// RegistryDAO.getPatientDAO().getPatientByID(study.getPatient().getIdpatient());
		// usuario
		Users user = patient.getUsers();
		// modalidad
		Equipment equipment = study.getEquipment();

		writeXML xml = new writeXML(path, study, user, patient, equipment);
		String fileName = path + xml.generateXML();

		logger.info("FileName: " + fileName);
		

		try {
			SendFile sendFile = new SendFile(mirthIP, mirthPort, fileName);
			// cambiamos el estado delestudio a PROGRAMADO
			study.setStatus(Estado.PROGRAMADO.toString());
			boolean message = RegistryDAO.getStudyDAO().modificaStudy(study);
			logger.info("Cambio de estado de estudio " + study.getIdstudy() + " :" + message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			//e.printStackTrace();
			error="Fallo en la conexión con el servidor Mirth";
			
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.debug("Username: " + username);

		ModelAndView mav = new ModelAndView("home");

		mav.addObject("username", username);
		mav.addObject("error", error);

		// obtenemos un listado de todos los estudios citados
		logger.info("Mostramos listado de estudios citados.");
		List<Study> todayStudies = RegistryDAO.getStudyDAO().getTodayStudies();
		logger.info("Estudios encontrados: " + todayStudies.size());
		for (Study study_aux : todayStudies) {
			logger.info(study_aux.getIdstudy() + " " + study_aux.getPatient().getIdpatient());
		}
		mav.addObject("todayStudies", todayStudies);

		return mav;

	}

}
