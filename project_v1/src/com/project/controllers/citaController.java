package com.project.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.UIDGenerator;
import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Equipment;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.util.SendMail;
import com.project.util.TimeUtil;

@Controller
public class citaController {

	private static final Logger logger = Logger.getLogger(citaController.class);

	@RequestMapping(value = "/nuevaCita/{inicio}/{fin}/{resource}", method = RequestMethod.GET)
	public ModelAndView nuevaCita(@PathVariable("inicio") String inicio, @PathVariable("fin") String fin,
			@PathVariable("resource") String resource, HttpSession session) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		logger.debug("/nuevaCita -> citaControler.nuevaCita()");
		logger.info("Seleccion de paciente.");
		ModelAndView mav = new ModelAndView("nuevaCita");

		mav.addObject("username", username);

		List<Patient> allPatient = RegistryDAO.getPatientDAO().getAllPatients();
		mav.addObject("allPatient", allPatient);

		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("Usuario", user);

		mav.addObject("inicio", inicio);
		mav.addObject("fin", fin);
		mav.addObject("resource", resource);

		return mav;
	}

	@RequestMapping(value = "/nuevaCita_2/{inicio}/{fin}/{resource}/{idPatient}", method = RequestMethod.GET)
	public ModelAndView nuevaCita_2(@PathVariable("inicio") String inicio, @PathVariable("fin") String fin,
			@PathVariable("resource") String resource, @PathVariable("idPatient") String idPatient,
			HttpSession session) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		logger.debug("/nuevaCita_2 -> citaControler.nuevaCita_2()");
		logger.info("Formulario para completar los datos de la cita.");
		ModelAndView mav = new ModelAndView("nuevaCita_2");

		mav.addObject("username", username);

		// Obtenemos todos los equipos
		List<Equipment> allEquipment = RegistryDAO.getEquipmentDAO().getAllEquipments();
		mav.addObject("allEquipment", allEquipment);

		// Obtenemos el patient por ID
		Patient patient = RegistryDAO.getPatientDAO().getPatientByID(idPatient);
		mav.addObject("patient", patient);

		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("Usuario", user);

		logger.info("Inicio: " + inicio);
		logger.info("Fin: " + fin);
		// La fecha de inicio y fin viene definida en un String del tipo
		// 2017-04-14T09:30:00
		// operamos en el String para obtener la fecha 14-04-2017 y la hora
		// 09:30
		String dia = inicio.substring(8, 10);
		String mes = inicio.substring(4, 8);
		String ano = inicio.substring(0, 4);
		String fecha = inicio.substring(8, 10) + inicio.substring(4, 8) + inicio.substring(0, 4);
		// String fecha = inicio.substring(0, 10);
		logger.info("fecha: " + fecha);

		String horaInicio = inicio.substring(11, 16);
		logger.info("Hora Inicio: " + horaInicio);
		String horaFin = fin.substring(11, 16);
		logger.info("Hora fin: " + horaFin);

		mav.addObject("inicio", inicio);
		mav.addObject("fin", fin);
		mav.addObject("fecha", fecha);
		mav.addObject("horaInicio", horaInicio);
		mav.addObject("horaFin", horaFin);

		return mav;
	}

	@RequestMapping(value = "/nuevaCita_2/{inicio}/{fin}/{resource}/{idPatient}", method = RequestMethod.POST)
	public ModelAndView nuevaCita_2(@PathVariable("inicio") String inicio, @PathVariable("fin") String fin,
			@PathVariable("resource") String resource, @PathVariable("idPatient") String idPatient,
			HttpServletRequest request, HttpSession session) throws DicomException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		logger.debug("/nuevaCita_2 POST-> citaControler.nuevaCita_2()");
		logger.info("Generacion de la nueva cita con los datos devueltos del formulario");
		ModelAndView mav = new ModelAndView("calendar");

		mav.addObject("username", username);

		// con los datos obtenidos del formulario generamos el estudio.
		String descripcion = request.getParameter("descripcion");
		String idequipment = request.getParameter("idequipment");
		String fecha = request.getParameter("fecha");
		String horaInicio = request.getParameter("horaInicio");
		String horaFin = request.getParameter("horaFin");

		logger.info(descripcion);
		logger.info(idequipment);
		logger.info(fecha);
		logger.info(horaInicio);
		logger.info(horaFin);

		// con estos datos y los de paciente generamos el estudio.
		Patient patient = RegistryDAO.getPatientDAO().getPatientByID(idPatient);
		Equipment equipment = RegistryDAO.getEquipmentDAO().getEquipmentByID(Integer.parseInt(idequipment));
		Study estudio = new Study(patient, equipment);
		// generamos el identificador unico de estudio
		UIDGenerator uidGenerator = new UIDGenerator();
		estudio.setDicomStudyInstanceUid(uidGenerator.getNewUID());
		logger.info("UID: " + estudio.getDicomStudyInstanceUid());
		// medico remitente
		estudio.setReferringPhysician("test");
		// seguro
		estudio.setIssuer("test");
		// requesting service
		estudio.setRequestingService("test");
		// ponemos el estado del estudio a programado
		estudio.setStatus("PROGRAMADO");
		// fecha y hora
		// 1. Creamos Date de los daots que hemos traido del formulario

		Date dateStart = TimeUtil.parseDate(fecha);
		Date dateEnd = TimeUtil.parseDate(fecha);

		int hour = Integer.parseInt(horaInicio.substring(0, 2));
		int minute = Integer.parseInt(horaInicio.substring(3, 5));

		dateStart.setHours(hour);
		dateStart.setMinutes(minute);
		estudio.setScheduledProcedureStepStartDateTime(dateStart);
		logger.info(dateStart.toString());

		hour = Integer.parseInt(horaFin.substring(0, 2));
		minute = Integer.parseInt(horaFin.substring(3, 5));
		dateEnd.setHours(hour);
		dateEnd.setMinutes(minute);
		estudio.setScheduledProcedureStepEndDateTime(dateEnd);
		logger.info(dateEnd.toString());

		// comprobamos que el estudio no se solapa con ningun otro estudio.
		List<Study> estudios = RegistryDAO.getStudyDAO().getStudyEquipoHorario(Integer.parseInt(idequipment),
				estudio.getScheduledProcedureStepStartDateTime(), estudio.getScheduledProcedureStepEndDateTime());
		if (estudios.size() != 0) {
			// hay solapamiento en la maquina
			mav.addObject("messageError",
					"No se puede generar el estudio porque el equipo seleccionado esta previamente seleccionado en esa franja horaria.");
			return mav;
		}
		estudios = RegistryDAO.getStudyDAO().getStudyPatientHorario(idPatient,
				estudio.getScheduledProcedureStepStartDateTime(), estudio.getScheduledProcedureStepEndDateTime());
		if (estudios.size() != 0) {
			// hay solapamiento del paciente
			mav.addObject("messageError",
					"No se puede generar el estudio porque el paciente seleccionado tiene una citacion previa en esa franja horaria.");
			return mav;
		}

		// introducimos el nuevo estudio en la BBDD
		RegistryDAO.getStudyDAO().addStudy(estudio);

		// enviamos un mail al usuario recordandole los datos de la cita
		SendMail sendMail = new SendMail(patient.getUsers().getEmail(), "test", request);
		sendMail.start();

		mav.addObject("messageSuccess", "El estudio se ha creado correctamente y se ha insertado en la base de datos");

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/calendar/cambiaCita", method = RequestMethod.POST)
	public String cambiaCita(HttpSession session, HttpServletRequest request) {

		String idstudy = request.getParameter("idstudy");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String modality = request.getParameter("modality");

		Date startDate = TimeUtil.parseDateTime(startTime);
		Date endDate = TimeUtil.parseDateTime(endTime);
		

		// recuperamos el estudio de la BBDD.
		Study estudio = RegistryDAO.getStudyDAO().getStudyByID(Integer.parseInt(idstudy));
		
		//recuperamos el primer equipo por modality de la BBDD
		Equipment equipo = RegistryDAO.getEquipmentDAO().getEquipmentByModality(modality);
		
		//introducimos los datos en el nuevo estudio
		estudio.setScheduledProcedureStepStartDateTime(startDate);
		estudio.setScheduledProcedureStepEndDateTime(endDate);
		estudio.setEquipment(equipo);

		// comprobamos que el estudio no se solapa con ningun otro estudio.
		List<Study> estudios = RegistryDAO.getStudyDAO().getStudyEquipoHorarioDistinto(Integer.parseInt(idstudy),
				estudio.getEquipment().getIdequipment(), estudio.getScheduledProcedureStepStartDateTime(),
				estudio.getScheduledProcedureStepEndDateTime());
		if (estudios.size() != 0) {
			// hay solapamiento en la maquina

			return "No se puede generar el estudio porque el equipo seleccionado esta previamente seleccionado en esa franja horaria.";
		}
		estudios = RegistryDAO.getStudyDAO().getStudyPatientHorarioDistinto(Integer.parseInt(idstudy), estudio.getPatient().getIdpatient(),
				estudio.getScheduledProcedureStepStartDateTime(), estudio.getScheduledProcedureStepEndDateTime());
		if (estudios.size() != 0) {
			// hay solapamiento del paciente

			return "No se puede generar el estudio porque el paciente seleccionado tiene una citacion previa en esa franja horaria.";
		}

		//actualizamos la BBDD

		RegistryDAO.getStudyDAO().modificaStudy(estudio);

		return "Actualizado correctamente";
	}

	@RequestMapping(value = "/modificaCita/{idstudy}", method = RequestMethod.GET)
	public ModelAndView modificaCita(@PathVariable("idstudy") String idstudy, HttpSession session) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		ModelAndView mav = new ModelAndView("updateStudy");

		// obtenemos el estudio a modificar
		Study estudio = RegistryDAO.getStudyDAO().getStudyByID(Integer.parseInt(idstudy));
		List<Patient> allPatient = RegistryDAO.getPatientDAO().getAllPatients();
		List<Equipment> allEquipment = RegistryDAO.getEquipmentDAO().getAllEquipments();

		String fecha = TimeUtil.fechaString(estudio.getScheduledProcedureStepStartDateTime());
		String horaInicio = TimeUtil.horaString(estudio.getScheduledProcedureStepStartDateTime());
		String horaFin = TimeUtil.horaString(estudio.getScheduledProcedureStepEndDateTime());

		mav.addObject("estudio", estudio);
		mav.addObject("allPatient", allPatient);
		mav.addObject("allEquipment", allEquipment);
		mav.addObject("username", username);
		mav.addObject("fecha", fecha);
		mav.addObject("horaInicio", horaInicio);
		mav.addObject("horaFin", horaFin);

		return mav;
	}

	@RequestMapping(value = "/modificaCita/{idstudy}", method = RequestMethod.POST)
	public ModelAndView modificaCita(@PathVariable("idstudy") String idstudy, HttpSession session,
			HttpServletRequest request) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// obtenemos todos los parametros enviados en el formulario
		String idpatient = request.getParameter("idpatient");
		String descripcion = request.getParameter("descripcion");
		String idequipment = request.getParameter("idequipment");
		String medicoRemitente = request.getParameter("medicoRemitente");
		String departamento = request.getParameter("departamento");
		String fecha = request.getParameter("fecha");
		String horaInicio = request.getParameter("horaInicio");
		String horaFin = request.getParameter("horaFin");

		ModelAndView mav = new ModelAndView("calendar");

		// obtenemos paciente, equipo y estudio.
		Patient patient = RegistryDAO.getPatientDAO().getPatientByID(idpatient);
		Equipment equipment = RegistryDAO.getEquipmentDAO().getEquipmentByID(Integer.parseInt(idequipment));
		Study estudio = RegistryDAO.getStudyDAO().getStudyByID(Integer.parseInt(idstudy));
		// modificamos los datos del estudio.
		estudio.setPatient(patient);
		estudio.setEquipment(equipment);
		estudio.setReferringPhysician(medicoRemitente);
		estudio.setRequestingService(departamento);
		estudio.setRequestedProcedureDescription(descripcion);

		// fecha y hora
		// 1. Creamos Date de los daots que hemos traido del formulario

		Date dateStart = TimeUtil.parseDate(fecha);
		Date dateEnd = TimeUtil.parseDate(fecha);

		int hour = Integer.parseInt(horaInicio.substring(0, 2));
		int minute = Integer.parseInt(horaInicio.substring(3, 5));

		dateStart.setHours(hour);
		dateStart.setMinutes(minute);
		estudio.setScheduledProcedureStepStartDateTime(dateStart);
		logger.info(dateStart.toString());

		hour = Integer.parseInt(horaFin.substring(0, 2));
		minute = Integer.parseInt(horaFin.substring(3, 5));
		dateEnd.setHours(hour);
		dateEnd.setMinutes(minute);
		estudio.setScheduledProcedureStepEndDateTime(dateEnd);
		logger.info(dateEnd.toString());

		// comprobamos que el estudio no se solapa con ningun otro estudio.
		List<Study> estudios = RegistryDAO.getStudyDAO().getStudyEquipoHorarioDistinto(Integer.parseInt(idstudy),
				Integer.parseInt(idequipment), estudio.getScheduledProcedureStepStartDateTime(),
				estudio.getScheduledProcedureStepEndDateTime());
		if (estudios.size() != 0) {
			// hay solapamiento en la maquina
			mav.addObject("messageError",
					"No se puede generar el estudio porque el equipo seleccionado esta previamente seleccionado en esa franja horaria.");
			return mav;
		}
		estudios = RegistryDAO.getStudyDAO().getStudyPatientHorarioDistinto(Integer.parseInt(idstudy), idpatient,
				estudio.getScheduledProcedureStepStartDateTime(), estudio.getScheduledProcedureStepEndDateTime());
		if (estudios.size() != 0) {
			// hay solapamiento del paciente
			mav.addObject("messageError",
					"No se puede generar el estudio porque el paciente seleccionado tiene una citacion previa en esa franja horaria.");
			return mav;
		}

		// actualizamos el estudio

		// introducimos el nuevo estudio en la BBDD
		RegistryDAO.getStudyDAO().modificaStudy(estudio);

		// enviamos un mail al usuario recordandole los datos de la cita
		SendMail sendMail = new SendMail(patient.getUsers().getEmail(), "Su cita se ha modifiacado", request);
		sendMail.start();

		mav.addObject("messageSuccess", "El estudio se ha creado correctamente y se ha insertado en la base de datos");
		mav.addObject("username", username);

		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public String cancelaCita(HttpSession session, HttpServletRequest request) {

		String idstudy = request.getParameter("idstudy");
		
		// recuperamos el estudio de la BBDD.
		Study estudio = RegistryDAO.getStudyDAO().getStudyByID(Integer.parseInt(idstudy));
		
		//cambiamos el status de la cita a CANCELADO
		estudio.setStatus("CANCELADO");
		
		//actualizamos el estudio en la BBDD
		RegistryDAO.getStudyDAO().modificaStudy(estudio);

		return "Estudio cancelado";
	}

}
