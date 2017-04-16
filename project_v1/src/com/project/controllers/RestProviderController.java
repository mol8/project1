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
	
	private static final Logger logger = Logger.getLogger(RestProviderController.class);
	
	@RequestMapping(value = "/calendar/jsonEvents", method = RequestMethod.GET)
	public String jsonEvents() throws ParseException {

		// obtenemos el usuario logeado de la autenticaci�n
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// obtenemos los estudiosprogramados
		List<Study> studiesProgramados = RegistryDAO.getStudyDAO().getStudyPROGRAMADOS();
		

		Date start;
		Date end;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		JSONArray jArray = new JSONArray();
		for (Study study : studiesProgramados) {
			JSONObject json = new JSONObject();
			
			start = study.getScheduledProcedureStepStartDateTime();
			end = study.getScheduledProcedureStepEndDateTime();
			
			json.put("title", study.getPatient().getUsers().getName());
			logger.info("title: "+study.getPatient().getUsers().getName());
			json.put("start", start);
			logger.info("start: "+ start);
			json.put("end", end);
			logger.info("end: "+ end);
			
			json.put("resourceId", study.getEquipment().getModality());
			logger.info("resourceId: "+study.getEquipment().getModality());
			
			json.put("idequipment", study.getEquipment().getIdequipment());
			logger.info("idequipment: "+study.getEquipment().getIdequipment());
			
			json.put("idstudy", study.getIdstudy());
			logger.info("istudy: "+study.getIdstudy());

			jArray.put(json);
		}
		return jArray.toString();
	}

}
