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

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.timepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery.timepicker.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-datepicker.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Datepair.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.datepair.js"></script>
<script>
	function defaultSelected() {
		var idequipment = '${estudio.getEquipment().getIdequipment()}';
		var idpatient = '${estudio.getPatient().getIdpatient()}';
			
		document.getElementById('idequipment').value = idequipment;
		document.getElementById('idpatient').value = idpatient;
	}
</script>




<title>Modificar datos de cita</title>
</head>
<body onload="defaultSelected();">

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home">INICIO</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/calendar">Calendario</a></li>
				<li><a href="${pageContext.request.contextPath}/userList">Lista
						de usuarios</a></li>
				<li><a href="${pageContext.request.contextPath}/patientList">Lista
						de pacientes</a></li>
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
			<h1>Modificacion de datos de citacion</h1>
			<p></p>
		</div>


		<!-- <label>${message}</label><br></br> -->
		<div>
			<form
				action="${pageContext.request.contextPath}/modificaCita/${estudio.getIdstudy()}"
				method="post">

				<section>
				<h2>Seleccion de Paciente</h2>

				<div class="form-group">
					<label>Paciente</label> <select id="idpatient" name="idpatient">
						<c:forEach items="${allPatient}" var="paciente">
							<option value="${paciente.getIdpatient()}">${paciente.getUsers().getName()}
								${paciente.getUsers().getSurename()} -
								${paciente.getIdpatient()}</option>
						</c:forEach>
					</select>
				</div>
				</section>
				<section>
				<h2>Modalidad y descripcion</h2>
				<div class="form-group">
					<label>Descripcion</label> <input id="descripcion" type="text"
						name="descripcion" class="form-control" value="${estudio.getRequestedProcedureDescription()}" />
				</div>

				<div class="form-group">
					<label>Modalidad</label> <select id="idequipment"
						name="idequipment">
						<c:forEach items="${allEquipment}" var="equipment">
							<option value="${equipment.getIdequipment()}">${equipment.getModality()}
								- ${equipment.getAeTitle()}</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group">
					<label>Medico Remitente</label> <select name="medicoRemitente">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
				</div>

				<div class="form-group">
					<label>Departamento</label> <select name="departamento">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
				</div>
				</section>


				<section>
				<h2>Fecha y Hora</h2>
				<%-- <label>Inicio</label> <input id="fecha" type="text" name="fecha"
					value="${inicio}" /> <label>Fin</label> <input id="horaInicio"
					type="text" name="horaInicio" value="${fin}" /> --%>
				<div class="form-group">
					<p id="datepairExample">

						<label>Fecha</label> <input type="text" id="fecha" name="fecha"
							class="date start" value="${fecha}" /> <label>Hora
							inicio</label><input type="text" id="horaInicio" name="horaInicio"
							class="time start" value="${horaInicio}" /> <label>Horafin</label>
						<input type="text" id="horaFin" name="horaFin" class="time end"
							value="${horaFin}" />
						<!-- 					<input type="text" class="date end" /> -->

					</p>
				</div>

				<script>
					// initialize input widgets first
					$('#datepairExample .time').timepicker({
						'showDuration' : false,
						'timeFormat' : 'H:i',
						'minTime' : '9:00',
						'maxTime' : '23:45',
						'step' : 15,
					});

					$('#datepairExample .date').datepicker({
						'format' : 'd-m-yyyy',
						'changeYear' : true,
						'changeMonth' : true,
						'autoclose' : true
					});

					// initialize datepair
					$('#datepairExample').datepair({
						'defaultTimeDelta' : 900000
					// milliseconds - 15 min
					});
				</script> </section>






				<input class="btn btn-primary pull-right" type="submit"
					value="Enviar" />
			</form>
		</div>

		<footer class="footer">
		<div class="container">
			<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
		</div>
		</footer>
</body>
</html>