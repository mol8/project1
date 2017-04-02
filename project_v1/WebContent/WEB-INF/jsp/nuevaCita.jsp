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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_filter.css">
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

${fecha}
${horaInicio}
${horaFin}

<input type="text" id="filter" onkeyup="filter()" placeholder="Busqueda por apellido"/>

<table border="1" id="myTable">
	<tr class="header">
		<th onclick="sortTable(0)">Nombre</th>
		<th onclick="sortTable(1)">Apellido</th>
		<th onclick="sortTable(2)">Identificador</th>
		<th onclick="sortTable(3)">Seleccionar</th>
	</tr>
	<c:forEach items="${allPatient}" var="patient">
		<tr>
			<td>${patient.users.name}</td>
			<td>${patient.users.getSurename()}</td>
			<td>${patient.getIdpatient()}</td>
			<!-- Boton para seleccionar el paciente -->
			
			<td>
				<input type="image" src="${pageContext.request.contextPath}/img/ok.jpg" id="selectPatient" onclick="window.location='${pageContext.request.contextPath}/nuevaCita_2/${fecha}/${horaInicio}/${horaFin}/${patient.getIdpatient()}';" style="width:25px; height: 25px;"/>
			</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>