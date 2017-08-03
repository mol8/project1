package com.project.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.project.controllers.loginController;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private static final Logger logger = Logger.getLogger(loginController.class);
	
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        // Get the role of logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        
        logger.info("determinateTergetUrl");
        logger.info("Role:"+role);

        String targetUrl = "";
        
        switch (role) {
		case "[ROLE_USUARIO]":
			targetUrl = "/homepatient";
			logger.info("targetUrl:"+targetUrl);
			break;
			
		case "[ROLE_ADMINISTRADOR]":
			targetUrl = "/home";
			logger.info("targetUrl:"+targetUrl);
			break;
			
		case "[ROLE_CLINICO]":
			targetUrl = "/home";
			logger.info("targetUrl:"+targetUrl);
			break;

		default:
			targetUrl = "/login";
			break;
		}
        	
        logger.info("targetUrl:"+targetUrl);
        return targetUrl;
    }
}
