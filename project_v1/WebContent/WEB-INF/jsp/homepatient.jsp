<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<title>Patient Home</title>
</head>
<body>



	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/homepatient">Estudios</a></li>
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
			<h1>Datos de Paciente</h1>
		</div>

		<div class="container-fluid">
			<div class="panel panel-default">
				<div class="panel-body text-left">
					<div class="row justify-content-md-center">
						<div class="col-md-6">
							<p>Nombre: ${patient.getUsers().getName()}</p>
							<p>Apellido: ${patient.getUsers().getSurename()}</p>
							<p>Identificacion: ${patient.getIdpatient()}</p>
						</div>
						<div class="col-md-6">
							<p>Fecha de Nacimiento: ${patient.getDateOfBirth()}</p>
							<p>Correo electronico: ${patient.getUsers().getSurename()}</p>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="container">
			<table class="table" id="myTable">
				<thead class="header">
					<tr class="header">
						<th onclick="sortTable(0)">Fecha</th>
						<th onclick="sortTable(1)">Hora</th>
						<th onclick="sortTable(2)">Descripcion</th>
						<th onclick="sortTable(3)">Modalidad</th>
						<th onclick="sortTable(4)">Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${studies}" var="study">
						<c:choose>
							<c:when test="${study.status=='ENTRADA'}">
								<tr id="row_${study.idstudy}" bgcolor="#FFFFCC">
							</c:when>
							<c:when test="${study.status=='FINALIZADO'}">
								<tr id="row_${study.idstudy}" bgcolor="#E5FFCC">
							</c:when>
							<c:when test="${study.status=='PROGRAMADO'}">
								<tr id="row_${study.idstudy}">
							</c:when>
						</c:choose>
						<td>${study.fechaString(study.getScheduledProcedureStepStartDateTime())}</td>
						<td>${study.horaString(study.getScheduledProcedureStepStartDateTime())}</td>
						<td>${study.requestedProcedureDescription}</td>
						<td>${study.equipment.modality}</td>
						<td>${study.status}</td>

						<c:choose>
							<c:when test="${study.status=='ENTRADA'}">
								<td><img
									src="${pageContext.request.contextPath}/img/xray.jpg"
									style="width: 25px; height: 25px;" /></td>
							</c:when>
							<c:when test="${study.status=='FINALIZADO'}">
								<td><input type="image"
									src="${pageContext.request.contextPath}/img/ver.png"
									onclick="window.open('${study.url}');return false;"
									style="width: 25px; height: 25px;" /></td>
							</c:when>
							<c:when test="${study.status=='PROGRAMADO'}">
								<td><input type="image"
									src="${pageContext.request.contextPath}/img/waitingroom.jpg"
									id="saveForm"
									onclick="window.location='${pageContext.request.contextPath}/entrada/${study.idstudy}';"
									style="width: 25px; height: 25px;" />

								<input type="image"
									src="${pageContext.request.contextPath}/img/calendario.jpg"
									id="saveForm"
									onclick="window.location='${pageContext.request.contextPath}/modificaCita/${study.idstudy}';"
									style="width: 25px; height: 25px;" />

								<input type="image"
									src="${pageContext.request.contextPath}/img/cancelar.jpg"
									id="btn_${study.idstudy}" onclick="cancelStudy(this.id)"
									style="width: 25px; height: 25px;" /></td>
							</c:when>
						</c:choose>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<footer class="footer">
		<div class="container">
			<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
		</div>
		</footer>
</body>
</html>