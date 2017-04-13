package com.project.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.dao.implementation.StudyDAO;
import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Study;

@RestController
public class RestProviderController {
	
	private static final Logger logger = Logger.getLogger(StudyDAO.class);
	
	@RequestMapping(value = "/calendar/jsonEvents", method = RequestMethod.GET)
	public String jsonEvents() throws ParseException {

		// obtenemos el usuario logeado de la autenticaci�n
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// obtenemos los estudiosprogramados
		List<Study> studiesProgramados = RegistryDAO.getStudyDAO().getStudyPROGRAMADOS();
		
		Calendar cal = Calendar.getInstance();
		Date start;
		Date end;
		String endString;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		JSONArray jArray = new JSONArray();
		for (Study study : studiesProgramados) {
			JSONObject json = new JSONObject();
			
			start = study.getScheduledProcedureStepStartDateTime();
			cal.setTime(study.getScheduledProcedureStepStartDateTime()); // sets calendar time/date
		    cal.add(Calendar.MINUTE, 30); // adds one hour
			//end= cal.getTime();
		    endString = dateFormat.format(cal.getTime());
			end = dateFormat.parse(dateFormat.format(cal.getTime()));
			
			json.put("title", study.getPatient().getUsers().getName());
			logger.info("title: "+study.getPatient().getUsers().getName());
			json.put("start", start);
			logger.info("start: "+ start);
			json.put("end", endString);
			logger.info("end: "+ endString);
			
			json.put("resourceId", study.getEquipment().getModality());
			logger.info("resourceId: "+study.getEquipment().getModality());

			jArray.put(json);
		}
		return jArray.toString();
	}

}
