package com.project.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;
import com.project.util.PasswordGenerator;
import com.project.util.SendMail;

@Controller
public class PatientController {
	
	private static final Logger logger = Logger.getLogger(PatientController.class);

	@RequestMapping(value = "/patientList", method = RequestMethod.GET)
	public ModelAndView patientList(HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/patientList -> PatientController.patientList()");
		logger.info("Muestra Listado de pacientes.");
		ModelAndView mav = new ModelAndView("patientList");
		
		mav.addObject("username",username);
		
		
		List<Patient> allPatients = RegistryDAO.getPatientDAO().getAllPatients();
		mav.addObject("allPatients", allPatients);
		
		mav.addObject("Usuario", username);
		
		return mav;
	}
	
	@RequestMapping(value = "/patient/update/{idpatient}", method = RequestMethod.GET)
	public ModelAndView patientUpdate(@PathVariable("idpatient") String idpatient,HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/patient/update/{idpatient} -> PatientController.patientUpdate()");
		logger.info("Actualiza datos de paciente.");
		ModelAndView mav = new ModelAndView("updatePatient");	
		mav.addObject("username",username);
		
		//obtenemos el paciente a actualizar
		Patient patient = RegistryDAO.getPatientDAO().getPatientByID(idpatient);
		mav.addObject("patient",patient);
		
		//obtenemos el usuario relacionado con el paciente
		Users user = patient.getUsers();
		mav.addObject("user", user);
		
		return mav;
	}
	
	@RequestMapping(value = "/patient/update/{idpatient}", method = RequestMethod.POST)
	public ModelAndView patientUpdate(@PathVariable("idpatient") String idpatient, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("patientList");
		
		logger.debug("/patient/update/{idpatient} POST -> PatientController.patientUpdate() POST");
		logger.info("Actualiza datos de paciente.");
		
		String username_log = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("username", username_log);

		String message = "Paciente actualizado satisfactoriamente";
		
		logger.info(request.getParameter("name"));
		logger.info(request.getParameter("surename"));
		logger.info(request.getParameter("username"));
		logger.info(request.getParameter("email"));
		logger.info(request.getParameter("password"));
		logger.info(request.getParameter("role"));

		try {
			String name_updated = request.getParameter("name");
			String surename_updated = request.getParameter("surename");
			String idpatient_updated = request.getParameter("idpatient");
			String username_updated = request.getParameter("username");
			
			//fecha de nacimiento en formato string a Date
			String testDate = request.getParameter("dateofbirth");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(testDate);
			
			Date dateofbirth_updated = date;
			String email_updated = request.getParameter("email");
			String password_updated = request.getParameter("password");
			String sexo_updated = request.getParameter("sexo");
			String role_updated = request.getParameter("role");
			
			Patient patient = RegistryDAO.getPatientDAO().getPatientByID(idpatient);
			
			patient.setIdpatient(idpatient_updated);
			patient.setDateOfBirth(date);
			patient.setSex(sexo_updated);
			
			Users usuario = RegistryDAO.getUsersDAO().getUserByID(patient.getUsers().getIduser());
			
			usuario.setName(name_updated);
			usuario.setSurename(surename_updated);
			usuario.setUsername(username_updated);
			usuario.setEmail(email_updated);
			usuario.setPassword(password_updated);
			usuario.setRole(role_updated);
			
			RegistryDAO.getUsersDAO().updateUser(usuario);
			RegistryDAO.getPatientDAO().modificaPatient(patient);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
			e.printStackTrace();
			message="Error en la actualizacion del paciente";
		}
		
		
		List<Patient> allPatients = RegistryDAO.getPatientDAO().getAllPatients();
		mav.addObject("allPatients", allPatients);

		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/patient/new", method = RequestMethod.GET)
	public ModelAndView newPatient(HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/patient/new -> PatientController.newPatient");
		logger.info("Creamos nuevo paciente");
		ModelAndView mav = new ModelAndView("newPatient");	
		
		return mav;
	}
	
	@RequestMapping(value = "/patient/new", method = RequestMethod.POST)
	public ModelAndView newPatient(HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("patientList");
		
		logger.debug("/patient/new POST -> PatientController.newPatient() POST");
		logger.info("Creamos nuevo paciente.");
		
		String username_log = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("username", username_log);

		String message = "Paciente creado satisfactoriamente";
		
		logger.info(request.getParameter("name"));
		logger.info(request.getParameter("surename"));
		logger.info(request.getParameter("username"));
		logger.info(request.getParameter("email"));
		logger.info(request.getParameter("password"));
		logger.info(request.getParameter("role"));

		try {
			String name_updated = request.getParameter("name");
			String surename_updated = request.getParameter("surename");
			String idpatient_updated = request.getParameter("idpatient");
			String username_updated = request.getParameter("username");
			
			//fecha de nacimiento en formato string a Date
			String testDate = request.getParameter("dateofbirth");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(testDate);
			
			Date dateofbirth_updated = date;
			String email_updated = request.getParameter("email");
			
			//genreamos un password aleatorio con utilidad gen PASSWORD
			String password_updated = PasswordGenerator.getPassword(PasswordGenerator.MINUSCULAS+PasswordGenerator.NUMEROS, 5);
			
			String sexo_updated = request.getParameter("sexo");
			String role_updated = request.getParameter("role");
			
			Patient patient = new Patient();
			
			patient.setIdpatient(idpatient_updated);
			patient.setDateOfBirth(date);
			patient.setSex(sexo_updated);
			
			Users usuario = new Users();
			
			usuario.setName(name_updated);
			usuario.setSurename(surename_updated);
			usuario.setUsername(username_updated);
			usuario.setEmail(email_updated);
			usuario.setPassword(password_updated);
			usuario.setRole(role_updated);
			
			RegistryDAO.getUsersDAO().addUser(usuario);
			usuario = RegistryDAO.getUsersDAO().getUserByUserName(username_updated);
			
			patient.setUsers(usuario);
			
			RegistryDAO.getPatientDAO().addPatient(patient);
			
			//Enviamos por correo electronico los datos de creacion de paciente.
			String mensage = "Estimado usuario, a continuacion encontrara sus datos de acceso:\n"
					+ "Usuaraio: "+username_updated+"\n"
					+ "Contrasena: "+password_updated;
			SendMail.sendMail(email_updated, mensage,request);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
			e.printStackTrace();
			message="Error en la creacion del paciente";
		}
		
		
		List<Patient> allPatients = RegistryDAO.getPatientDAO().getAllPatients();
		mav.addObject("allPatients", allPatients);

		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/patient/viewStudies/{idpatient}", method = RequestMethod.GET)
	public ModelAndView viewStudies(@PathVariable("idpatient") String idpatient,HttpSession session,HttpServletRequest request) {
		logger.debug("/patient/viewStudies/{idpatient} -> PatientController.viewStudies()");
		logger.info("Ver estudios de paciente.");	
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		ModelAndView mav = new ModelAndView("viewStudies");
		
		mav.addObject("username",username);
		
		//obtenemos estudios finalizados del paciente
		List<Study> studyList = RegistryDAO.getStudyDAO().getEndStudiesByidPatient(idpatient);
		mav.addObject("studyList",studyList);

		return mav;
	}

}
