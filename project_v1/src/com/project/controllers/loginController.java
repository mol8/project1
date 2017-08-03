package com.project.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;

@Controller
public class loginController {
	
	private static final Logger logger = Logger.getLogger(loginController.class);
	
	
	@RequestMapping(value = "/login", method= RequestMethod.GET)
	public ModelAndView login(){
		logger.debug("/login->login()");		
		logger.info("Spring Login method has been called");
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(){
		logger.info("login successfull");		 
		//obtenemos el username del securityContextHolder
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.debug("Username: "+username);
		
		ModelAndView mav = new ModelAndView("home");
		
		mav.addObject("username",username);
		
		//obtenemos un listado de los estudios programados para el dia de hoy
		logger.info("Mostramos listado de estudios programados.");
		List<Study> todayStudies = RegistryDAO.getStudyDAO().getTodayStudies();
		logger.info("Estudios encontrados: " + todayStudies.size());
		for (Study study : todayStudies) {
			logger.info(study.getIdstudy() + " " + study.getPatient().getIdpatient());
		}
		mav.addObject("todayStudies",todayStudies);
		
		return mav;
	}
	
	@RequestMapping(value = "/homepatient", method = RequestMethod.GET)
	public ModelAndView homePatient(){
		logger.info("login successfull AS PATIENT");		 
		//obtenemos el username del securityContextHolder
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.debug("Username: "+username);
		
		
		
		//obtenemos el usuario por nombre de usuario.
		logger.info("obtenemos los datos de usuario");
		Users user = RegistryDAO.getUsersDAO().getUserByUserName(username);
		//obtenemos el paciente del usuario
		Patient patient = new Patient();
		Set<Patient> setPatient = user.getPatients();
		Iterator<Patient> patientIterator=setPatient.iterator();
		while(patientIterator.hasNext()){
			patient = patientIterator.next();
		}
		
		//obtenemos un listado de los estudios del paciete
		logger.info("Mostramos listado de estudios finalizados.");
		List<Study> studies = RegistryDAO.getStudyDAO().getStudiesByidPatient(patient.getIdpatient());
		logger.info("Estudios encontrados: " + studies.size());
		for (Study study : studies) {
			logger.info(study.getIdstudy() + " " + study.getPatient().getIdpatient());
		}
		
		ModelAndView mav = new ModelAndView("homepatient");
		mav.addObject("patient",patient);
		mav.addObject("username",username);
		mav.addObject("studies",studies);
		
		return mav;
	}

}
