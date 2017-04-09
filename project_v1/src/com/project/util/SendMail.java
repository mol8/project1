package com.project.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.project.controllers.PatientController;

public class SendMail {
	
	private static final Logger logger = Logger.getLogger(SendMail.class);
	
	 public static void sendMail(String emailDestino, String mensage, HttpServletRequest request) {
	        Properties propsEmail = new Properties();
 
	        Properties prop = new Properties();
	    	InputStream input = null;

	    	try {

	    		String path = request.getSession().getServletContext().getRealPath("/") + "//WEB-INF//resources//";
	    		input = new FileInputStream(path + File.separator +"email.properties");


	    		// load a properties file
	    		prop.load(input);

	    		// get the property value and print it out
	    		logger.info(prop.getProperty("mail.smtp.auth"));
	    		logger.info(prop.getProperty("mail.smtp.starttls.enable"));
	    		logger.info(prop.getProperty("mail.smtp.host"));
	    		logger.info(prop.getProperty("mail.smtp.port"));
	    		logger.info(prop.getProperty("username"));
	    		logger.info(prop.getProperty("password"));
	    		
	    		String username=prop.getProperty("username");
	    		String password=prop.getProperty("password");
	    		
		        propsEmail.put("mail.smtp.auth", prop.getProperty("mail.smtp.auth"));
		        propsEmail.put("mail.smtp.starttls.enable", prop.getProperty("mail.smtp.starttls.enable"));
		        propsEmail.put("mail.smtp.host", prop.getProperty("mail.smtp.host"));
		        propsEmail.put("mail.smtp.port", prop.getProperty("mail.smtp.port"));
		        
		        
		        Session session = Session.getInstance(propsEmail,
		                new javax.mail.Authenticator() {
		                    protected PasswordAuthentication getPasswordAuthentication() {
		                        return new PasswordAuthentication(username, password);
		                    }
		                });
		        
		        Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,
	                    InternetAddress.parse(emailDestino));
	            message.setSubject("Alta de usuario");
	            message.setText(mensage);
	 
	            Transport.send(message);
		        
		        

	    	} catch (MessagingException e) {
	            throw new RuntimeException(e);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	    		if (input != null) {
	    			try {
	    				input.close();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    	}
	 }
	 
	 public static void sendMailTest(String emailDestino, String mensage) {
	        Properties propsEmail = new Properties();

	        Properties prop = new Properties();
	    	InputStream input = null;

	    	try {
		        
		        String username="a4aceed1f34d00";
	    		String password="333379e6f06ac4";
	    		
		        propsEmail.put("mail.smtp.auth", "true");
		        propsEmail.put("mail.smtp.starttls.enable", "true");
		        propsEmail.put("mail.smtp.host", "smtp.mailtrap.io");
		        propsEmail.put("mail.smtp.port", "2525");
		        
		        Session session = Session.getInstance(propsEmail,
		                new javax.mail.Authenticator() {
		                    protected PasswordAuthentication getPasswordAuthentication() {
		                        return new PasswordAuthentication(username, password);
		                    }
		                });
		        
		        Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,
	                    InternetAddress.parse(emailDestino));
	            message.setSubject("Alta de usuario");
	            message.setText(mensage);
	 
	            Transport.send(message);
		        
		        

	    	} catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }finally {
	    		if (input != null) {
	    			try {
	    				input.close();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    	}
	 }

}
