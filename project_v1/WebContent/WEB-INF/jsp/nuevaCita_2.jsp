<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/tableFilter.js"></script>
	<script src="${pageContext.request.contextPath}/js/sortTable.js"></script>
<title>Seleccion de paciente - Nueva Cita</title>
</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Users app</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/calendar">Calendario</a></li>
				<li><a href="${pageContext.request.contextPath}/userList">Lista
						de usuarios</a></li>
				<li><a href="${pageContext.request.contextPath}/patientList">Lista
						de pacientes</a></li>
				<li class="active"><a
					href="${pageContext.request.contextPath}/home">Inicio</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><p class="navbar-text">${username}</p></li>
				<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

	<div class="container">
		<div class="jumbotron">
			<h1>Generacion de cita</h1>
			<p>Datos para la generacion de nueva cita</p>
		</div>


		<!-- <label>${message}</label><br></br> -->
		<div>
			<form
				action="${pageContext.request.contextPath}/nuevaCita_2/${inicio}/${fin}/${patient.getIdpatient()}"
				method="post" enctype="multipart/form-data"
				onsubmit="return dovalidations()">

				<section>
				<h2>Fecha y Hora</h2>
				<label>Inicio</label> <input id="fecha" type="text" name="fecha"
					value="${inicio}" /> <label>Fin</label> <input id="horaInicio"
					type="text" name="horaInicio" value="${fin}" /> </section>

				<section>
				<h2>Datos de paciente</h2>
				<label>Nombre</label> <input id="patientName" type="text"
					name="patientName" value="${patient.users.getName()}" /> <label>Apellidos</label>
				<input id="patientSurename" type="text" name="patientSurename"
					value="${patient.users.getSurename()}" /> <label>Identificador</label>
				<input id="patientId" type="text" name="patientId"
					value="${patient.getIdpatient()}" /> </section>

				<input type="submit" value="Enviar" />
			</form>
		</div>

		<footer class="footer">
		<div class="container">
			<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
		</div>
		</footer>
</body>
</html>