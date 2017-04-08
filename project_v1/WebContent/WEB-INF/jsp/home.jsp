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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Home</title>
</head>
<body>

<h2>Página Principal</h2>
<h3>Usuario=${username}</h3>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Users app</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/calendar">Calendario</a></li>
				<li><a href="${pageContext.request.contextPath}/userList">Lista de usuarios</a></li>
				<li><a href="${pageContext.request.contextPath}/home">Inicio</a></li>
			</ul>		
			<ul class="nav navbar-nav navbar-right">
				<li><p class="navbar-text">${username}</p></li>
				<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

<hr />

<table border="1" id="tablaEstudios">
	<tr>
		<th>Fecha</th>
		<th>Hora</th>
		<th>Paciente</th>
		<th>Descripcion</th>
		<th>Modalidad</th>
		<th>Estado</th>
		<th>Acciones</th>
	</tr>
	<c:forEach items="${allStudies}" var="study">
		<tr>
			<td>${study.fechaString()}</td>
			<td>${study.horaString()}</td>
			<td>${study.patient.users.name}</td>
			<td>${study.requestedProcedureDescription}</td>
			<td>${study.equipment.modality}</td>
			<td>${study.status}</td>
			<!-- Boton para dar entrada al estudio en la worklist -->
			
			<td>
				<input type="image" src="${pageContext.request.contextPath}/img/waitingroom.jpg" id="saveForm" onclick="window.location='${pageContext.request.contextPath}/entrada/${study.idstudy}';" style="width:25px; height: 25px;"/>
				<input type="button" value="Entrada" id="btnEntrada" onclick="window.location='${pageContext.request.contextPath}/entrada/${study.idstudy}';"></input>
			</td>
		</tr>
	</c:forEach>
</table>

</body>
</html>