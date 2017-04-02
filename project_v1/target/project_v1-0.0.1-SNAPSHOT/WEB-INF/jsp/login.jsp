<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Login page</title>
</head>
<body>

	<h1>Login here</h1>
	<%String error = request.getParameter("error");
	if(error != null){%>
	<label style="color: red;">Usuario o contraseña erroneos.</label>
	<%}%>
	
	<form action="${pageContext.request.contextPath}/doLogin" method="post">	
		<table>
			<tr>
				<td><label>Enter username:</label></td>
				<td><input type="text" name="usuario" /></td>
			</tr>
			<tr>
				<td><label>Enter password:</label></td>
				<td><input type="password" name="password"/></td>
	
			</tr>
	
			<tr>
				<td>&nbsp</td>
				<td><input align="middle" type="submit" value="login" /></td>
			</tr>
	
		</table>	
	</form>

</body>
</html>