package com.project.controllers;

import java.util.List;

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

@Controller
public class citaController {
	
private static final Logger logger = Logger.getLogger(citaController.class);
	

	@RequestMapping(value = "/nuevaCita/{fecha}/{horaInicio}/{horaFin}", method = RequestMethod.GET)
	public ModelAndView nuevaCita(@PathVariable("fecha") String fecha, @PathVariable("horaInicio") String horaInicio, @PathVariable("horaFin") String horaFin, HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/nuevaCita -> citaControler.nuevaCita()");
		logger.info("Seleccion de paciente.");
		ModelAndView mav = new ModelAndView("nuevaCita");
		
		mav.addObject("username",username);
		
		List<Patient> allPatient = RegistryDAO.getPatientDAO().getAllPatients();
		mav.addObject("allPatient", allPatient);
		
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("Usuario", user);
		
		mav.addObject("fecha", fecha);
		mav.addObject("horaInicio", horaInicio);
		mav.addObject("horaFin", horaFin);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/nuevaCita_2/{fecha}/{horaInicio}/{horaFin}/{idPatient}", method = RequestMethod.GET)
	public ModelAndView nuevaCita_2(@PathVariable("fecha") String fecha, @PathVariable("horaInicio") String horaInicio, @PathVariable("horaFin") String horaFin, @PathVariable("idPatient") String idPatient, HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/nuevaCita_2 -> citaControler.nuevaCita_2()");
		logger.info("Formulario para completar los datos de la cita.");
		ModelAndView mav = new ModelAndView("nuevaCita_2");
		
		mav.addObject("username",username);
		
		
		//Obtenemos el patient por ID
		Patient patient = RegistryDAO.getPatientDAO().getPatientByID(idPatient);
		mav.addObject("patient", patient);
		
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("Usuario", user);
		
		mav.addObject("fecha", fecha);
		mav.addObject("horaInicio", horaInicio);
		mav.addObject("horaFin", horaFin);
		
		return mav;
	}
	
	
	/*@RequestMapping(value = "/modificaUsuario/{idUsuario}", method = RequestMethod.POST)
	public ModelAndView modificaUsuario(@PathVariable("idUsuario") int idUsuario, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("modificaUsuario");
		
		String admin = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("admin", admin);
	
		
		
		String message = "Usuario actualizado satisfactoriamente";

		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> data = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

				String usuario = data.get(0).getString();
				String password = data.get(1).getString();
				String repassword = data.get(2).getString();
				String nombre = data.get(3).getString();
				String apellidos = data.get(4).getString();
				String role = data.get(5).getString();

				String imagen_usuario = new File(data.get(6).getName()).getName();

				if (password.equals(repassword)) {

					Usuarios usuarios = RegistryDAO.getUsuariosDAO().obtenUsuario(idUsuario);
					usuarios.setUsuario(usuario);
					usuarios.setPassword(password);
					usuarios.setNombre(nombre);
					usuarios.setApellidos(apellidos);
					usuarios.setRole(role);
					usuarios.setImagen_usuario(imagen_usuario);
					
					mav.addObject("usuarioModificar", usuarios);

					message = RegistryDAO.getUsuariosDAO().modificaUsuario(usuarios);

					// obtenemos el path real para guardar la imgagen en el
					// servidor
					String path = request.getSession().getServletContext().getRealPath("/") + "//WEB-INF//images//";
					// ahora guardamos la imagen en el directorio de im�genes
					data.get(6).write(new File(path + File.separator + imagen_usuario));

				} else {
					// los password no coinciden
					message = "El password introducido no coincide con la confirmaci�n de password";

				}

			} catch (Exception e) {
				message = "Ha sucedido alg�n error por favro pruebe de nuevo";

			}
		}

		mav.addObject("message", message);
		return mav;
	}*/

}
