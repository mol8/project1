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
<title>Seleccion de paciente - Nueva Cita</title>
</head>
<body>

<script src="${pageContext.request.contextPath}/js/tableFilter.js"></script>
<script src="${pageContext.request.contextPath}/js/sortTable.js"></script>

<h2>Página Principal</h2>
<h3>Usuario=${username}</h3>

<a href="${pageContext.request.contextPath}/logout">Logout</a>

<a href="${pageContext.request.contextPath}/calendar">Calendario</a>

<hr />

<!-- <label>${message}</label><br></br> -->
	<div>
		<form
			action="${pageContext.request.contextPath}/nuevaCita_2/${fecha}/${horaInicio}/${horaFin}/${patient.getIdpatient()}"
			method="post" enctype="multipart/form-data"
			onsubmit="return dovalidations()">

			<section>
				<h2>Fecha y Hora</h2>
				<label>Fecha</label> <input id="fecha" type="text" name="fecha"value="${fecha}" /> 
				<label>Hora inicio:</label> <input id="horaInicio" type="text" name="horaInicio" value="${horaInicio}" />
				<label>Hora fin:</label> <input id="horaFin" type="text" name="horaFin" value="${horaFin}" /> 
			</section>
			
			<section>
				<h2>Datos de paciente</h2>
				<label>Nombre</label> <input id="patientName" type="text" name="patientName"value="${patient.users.getName()}" /> 
				<label>Apellidos</label> <input id="patientSurename" type="text" name="patientSurename" value="${patient.users.getSurename()}" />
				<label>Identificador</label> <input id="patientId" type="text" name="patientId" value="${patient.getIdpatient()}" /> 
			</section>
			
			<input type="submit" value="Enviar" />
		</form>
	</div>

</body>
</html>