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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/js/tableFilter.js"></script>
<script src="${pageContext.request.contextPath}/js/sortTable.js"></script>
<script>
	
	function hide (elements) {	
	  elements = elements.length ? elements : [elements];
	  if ('${message}'==""){
		  for (var index = 0; index < elements.length; index++) {
			    elements[index].style.display = 'none';
			  }
		}
	}
</script>

<title>Patient List</title>
</head>
<body onload="hide(document.querySelectorAll('.alert'));">



	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home">INICIO</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/calendar">Calendario</a></li>
				<li><a href="${pageContext.request.contextPath}/userList">Lista de usuarios</a></li>
				<li class="active"><a href="${pageContext.request.contextPath}/patientList">Lista de pacientes</a></li>
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
			<h1>Lista de pacientes</h1>
			<p>Pacientes almacenados en el sistema</p>
		</div>
		
		<div class="alert alert-success" role="alert">${message}</div>

		<div class="input-group" style="margin-top: 10px;margin-bottom: 10px;">
			<span class="input-group-addon">Buscar</span> <input id="filter"
				type="text" class="form-control" onkeyup="filter()"
				placeholder="nombre">
		</div>

		<table class="table" id="myTable">
			<thead class="header">
				<tr class="header">
					<th onclick="sortTable(0)">Nombre</th>
					<th onclick="sortTable(1)">Apellido</th>
					<th onclick="sortTable(2)">Identificador</th>
					<th onclick="sortTable(3)">Nacimiento</th>
					<th onclick="sortTable(4)">Sexo</th>
					<th >Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allPatients}" var="patient">
					<tr>
						<td>${patient.getUsers().getName()}</td>
						<td>${patient.getUsers().getSurename()}</td>
						<td>${patient.getIdpatient()}</td>
						<td>${patient.getDateOfBirth()}</td>
						<td>${patient.getSex()}</td>
						<td>
							<a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/patient/update/${patient.getIdpatient()}" title="Update">update</a> 					
							<a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/patient/viewStudies/${patient.getIdpatient()}" title="ViewStudies">Ver Estudios</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="input-group" style="margin-top: 10px;margin-bottom: 10px;">
			<form action="${pageContext.request.contextPath}/patient/new">
				<input class="btn btn-default" type="submit"
					value="Crear nuevo usuario" />
			</form>
		</div>

	</div>
	
	
	
	<footer class="footer" style="margin-top: 10px;">
		<div class="container">
			<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
		</div>
	</footer>

</body>
</html>