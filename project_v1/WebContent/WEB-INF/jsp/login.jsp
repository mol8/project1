<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

 <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/css/singin.css" rel="stylesheet">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Login page</title>
</head>
<body>
	<div class="container">
		
		<form class="form-signin" action="${pageContext.request.contextPath}/doLogin" method="post">

			<h2 class="form-signin-heading">Login</h2>
			<%
				String error = request.getParameter("error");
				if (error != null) {
			%>
			<label style="color: red;">Usuario o contraseña erroneos.</label>
			<%
				}
			%>


			<label class="sr-only">Enter username:</label> 
			<input type="text" name="usuario" class="form-control" placeholder="User name" required autofocus /> 
			<label class="sr-only">Enter password:</label> 
			<input type="password" name="password" class="form-control" placeholder="Password" required /> 
			<input class="btn btn-lg btn-primary btn-block" align="middle" type="submit" value="login" />
		</form>
	</div>
</body>
</html>