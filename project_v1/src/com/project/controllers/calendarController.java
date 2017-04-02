package com.project.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class calendarController {
	
	private static final Logger logger = Logger.getLogger(calendarController.class);
	
	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public ModelAndView calendar(){
		logger.debug("/calendar -> calendarController.calendar()");
		logger.info("Cargamos el calendario.");
		ModelAndView mav = new ModelAndView("calendar");
		return mav;
	}
	

}
