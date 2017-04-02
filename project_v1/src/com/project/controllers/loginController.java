package com.project.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Study;

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
		
		//obtenemos un listado de todos los estudios citados
		logger.info("Mostramos listado de estudios citados.");
		List<Study> allStudies = RegistryDAO.getStudyDAO().getAllStudies();
		logger.info("Estudios encontrados: " + allStudies.size());
		for (Study study : allStudies) {
			logger.info(study.getIdstudy() + " " + study.getPatient().getIdpatient());
		}
		mav.addObject("allStudies",allStudies);
		
		return mav;
	}

}
