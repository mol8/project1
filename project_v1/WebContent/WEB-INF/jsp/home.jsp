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
	
	<script src="http://code.jquery.com/jquery-latest.min.js"
        type="text/javascript"></script>
	
	<script type="text/javascript">
		function cancelStudy(id){
			idstudy=id.split("_")[1];

			$.ajax({
				url:"${pageContext.request.contextPath}/cancelar",
				type: "post",
				data: "idstudy="+idstudy,

				success: function(response){
					var table = document.getElementById("myTable");
					var tr = document.getElementById("row_"+idstudy);
					table.deleteRow(tr.rowIndex);
					alert(response);
				},

				error: function(error){
					alert(error);
				}
			});
		}
	</script>

	<title>Home</title>
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
			<h1>Estudios Programados</h1>
			<p>Selecciona el estudio para dar entrada</p>
		</div>

		<div class="input-group"
			style="margin-top: 10px; margin-bottom: 10px;">
			<span class="input-group-addon">Buscar</span> <input id="filter"
				type="text" class="form-control" onkeyup="filter()"
				placeholder="nombre">
		</div>

		<table class="table" id="myTable">
			<thead class="header">
				<tr class="header">
					<th onclick="sortTable(0)">Paciente</th>
					<th onclick="sortTable(1)">Fecha</th>
					<th onclick="sortTable(2)">Hora</th>
					<th onclick="sortTable(3)">Descripcion</th>
					<th onclick="sortTable(4)">Modalidad</th>
					<th onclick="sortTable(5)">Estado</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todayStudies}" var="study">
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
						<td>${study.patient.users.name}</td>
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
							<td></td>
							<td></td>
						</c:when>
						<c:when test="${study.status=='FINALIZADO'}">
							<td><input type="image"
							src="${pageContext.request.contextPath}/img/ver.png"
							style="width: 25px; height: 25px;" /></td>
							<td></td>
							<td></td>
						</c:when>
						<c:when test="${study.status=='PROGRAMADO'}">
							<td><input type="image"
							src="${pageContext.request.contextPath}/img/waitingroom.jpg"
							id="saveForm"
							onclick="window.location='${pageContext.request.contextPath}/entrada/${study.idstudy}';"
							style="width: 25px; height: 25px;" /></td>

							<td><input type="image"
							src="${pageContext.request.contextPath}/img/calendario.jpg"
							id="saveForm"
							onclick="window.location='${pageContext.request.contextPath}/modificaCita/${study.idstudy}';"
							style="width: 25px; height: 25px;" /></td>

							<td><input type="image"
							src="${pageContext.request.contextPath}/img/cancelar.jpg"
							id="btn_${study.idstudy}"
							onclick="cancelStudy(this.id)"
							style="width: 25px; height: 25px;" /></td>
						</c:when>
					</c:choose>

						
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<footer class="footer" style="margin-top: 10px;">
	<div class="container">
		<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
	</div>
	</footer>

</body>
</html>