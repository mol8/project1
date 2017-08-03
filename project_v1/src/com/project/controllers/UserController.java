package com.project.controllers;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Patient;
import com.project.pojo.Users;

@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ModelAndView userList(HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/userList -> UserController.userList()");
		logger.info("Muestra Listado de usurios.");
		ModelAndView mav = new ModelAndView("userList");
		
		mav.addObject("username",username);
		
		List<Users> allUsers = RegistryDAO.getUsersDAO().getAllUsers();
		mav.addObject("allUsers", allUsers);
		
		mav.addObject("Usuario", username);
		
		return mav;
	}
	
	/*@RequestMapping(value = "/users/update/{iduser}", method = RequestMethod.GET)
	public ModelAndView userUpdate(@PathVariable("iduser") String iduser,HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/userUpdate/{iduser} -> UserController.userUpdate()");
		logger.info("Actualiza datos de usuario.");
		ModelAndView mav = new ModelAndView("userUpdate");	
		mav.addObject("username",username);
		int aux = Integer.parseInt(iduser); 
		Users user = RegistryDAO.getUsersDAO().getUserByID(aux);
		mav.addObject("user", user);
		
		
		return mav;
	}*/
	
	@RequestMapping(value = "/users/update/{iduser}", method = RequestMethod.GET)
	public String userUpdate(@PathVariable(value="iduser") String iduser,HttpSession session, Model model){
		
		logger.debug("/userUpdate/{iduser} -> UserController.userUpdate()");
		logger.info("Actualiza datos de usuario.");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username",username);
		int aux = Integer.parseInt(iduser);
		model.addAttribute("user", RegistryDAO.getUsersDAO().getUserByID(aux));
		
		ModelAndView mav = new ModelAndView("userUpdate");	
		mav.addObject("username",username);
		
		
		
		return "userUpdate";
	}
	

	@RequestMapping(value = "/users/update/{iduser}", method = RequestMethod.POST)
	public ModelAndView userUpdate(@PathVariable("iduser") int iduser, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("userList");
		
		logger.debug("/users/update/{iduser} POST -> UserController.userUpdate() POST");
		logger.info("Actualiza datos de usuario.");
		
		String username_log = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("username", username_log);

		String message = "Usuario actualizado satisfactoriamente";
		
		logger.info(request.getParameter("name"));
		logger.info(request.getParameter("surename"));
		logger.info(request.getParameter("username"));
		logger.info(request.getParameter("email"));
		logger.info(request.getParameter("password"));
		logger.info(request.getParameter("role"));

		String name = request.getParameter("name");
		String surename = request.getParameter("surename");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		
		Users usuario = RegistryDAO.getUsersDAO().getUserByID(iduser);
		
		usuario.setName(name);
		usuario.setSurename(surename);
		usuario.setUsername(username);
		usuario.setEmail(email);
		usuario.setPassword(password);
		usuario.setRole(role);
		
		RegistryDAO.getUsersDAO().updateUser(usuario);
		
		List<Users> allUsers = RegistryDAO.getUsersDAO().getAllUsers();
		mav.addObject("allUsers", allUsers);

		mav.addObject("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/users/new", method = RequestMethod.GET)
	public ModelAndView newUser(HttpSession session){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.debug("/users/new -> UserController.newUser");
		logger.info("Creamos nuevo usuario");
		ModelAndView mav = new ModelAndView("nuevoUsuario");	
		
		return mav;
	}
	
	@RequestMapping(value = "/users/new", method = RequestMethod.POST)
	public ModelAndView newUser(HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("userList");
		
		logger.debug("/users/new POST -> UserController.newUser() POST");
		logger.info("Crea nuevo usario en BD.");
		
		String username_log = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("username", username_log);
		
		logger.info(request.getParameter("name"));
		logger.info(request.getParameter("surename"));
		logger.info(request.getParameter("username"));
		logger.info(request.getParameter("email"));
		logger.info(request.getParameter("password"));
		logger.info(request.getParameter("role"));

		String name = request.getParameter("name");
		String surename = request.getParameter("surename");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		
		Users usuario = new Users();
		
		usuario.setName(name);
		usuario.setSurename(surename);
		usuario.setUsername(username);
		usuario.setEmail(email);
		usuario.setPassword(password);
		usuario.setRole(role);
		
		RegistryDAO.getUsersDAO().addUser(usuario);
		
		List<Users> allUsers = RegistryDAO.getUsersDAO().getAllUsers();
		mav.addObject("allUsers", allUsers);

		String message = "Usuario creado satisfactoriamente";
		mav.addObject("message", message);
		return mav;
	}
	
	
	@RequestMapping(value = "/users/delete/{iduser}", method = RequestMethod.GET)
	public ModelAndView userDelete(@PathVariable(value="iduser") String iduser,HttpSession session){
		
		ModelAndView mav = new ModelAndView("userList");
		
		logger.debug("/userUpdate/{iduser} -> UserController.userUpdate()");
		logger.info("Actualiza datos de usuario.");
		
		String message = "";
		String error = "";
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		mav.addObject("username",username);
		Users userLogogged = RegistryDAO.getUsersDAO().getUserByUserName(username);
		int aux = Integer.parseInt(iduser);
		//model.addAttribute("user", RegistryDAO.getUsersDAO().getUserByID(aux));
		
		Users usuario = RegistryDAO.getUsersDAO().getUserByID(aux);
		
		/*//eliminamos el usuario, pero primero tenemos que ver si hay pacientes enlazados con este usuario para ponerlos a NULL antes de borrar el usuario.
		Patient patient = RegistryDAO.patientDAO.getPatientByUser(aux);
		patient.setUsers(null);
		//actualizamos el paciente con user null
		RegistryDAO.patientDAO.modificaPatient(patient);
		
		//borramos el usuario
		RegistryDAO.usersDAO.deleteUser(aux);*/
		
		//comprobamos que el usuario que tratamos de borrar no sea el usuario logeado
		if(userLogogged.getUsername().equals(usuario.getUsername())){
			logger.info("No se puede borrar el usuario logueado");
			error="No se puede borrar el usuario logueado";
			
		}else{
			//no borramos el usuario.
			//ponemos su role como NO_ACTIVO
			usuario.setRole("NO_ACTIVO");
			RegistryDAO.usersDAO.updateUser(usuario);
			message="Usuario "+ usuario.getUsername() +" desactivado correctamente";
		}
		
		
		mav.addObject("message", message);
		mav.addObject("error", error);
		
		List<Users> allUsers = RegistryDAO.getUsersDAO().getAllUsers();
		mav.addObject("allUsers", allUsers);
		
		return mav;
	}
	
}
