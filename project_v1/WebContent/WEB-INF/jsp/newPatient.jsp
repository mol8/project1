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
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

		<!-- jQuery library -->
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

		<!-- Latest compiled JavaScript -->
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script>
		  $( function() {
			  $( "#dateofbirth" ).datepicker({
				  dateFormat: "yy-mm-dd"
				});
			  } );
		</script>
		<title>NEW patient</title>
</head>
<body>
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
			<h1>Nuevo paciente</h1>
		</div>
		<form
			action="${pageContext.request.contextPath}/patient/new"
			method="post">
			<div class="form-group">
				<label for="name">Nombre</label> <input name="name"
					class="form-control" value="" />
			</div>
			<div class="form-group">
				<label for="surename">Apellidos</label> <input name="surename"
					class="form-control" value="" />
			</div>
			<div class="form-group">
				<label for="idpatient">Identificador</label> <input name="idpatient"
					class="form-control" value="" />
			</div>
			<div class="form-group">
				<label for="username">Nombre de usuario</label> <input
					name="username" class="form-control" value="" />
			</div>
			<div class="form-group">
				<label for="dateofbirth">Fecha de Nacimiento</label> <input
					type="text" id="dateofbirth" name="dateofbirth" class="form-control"
					value="" />
			</div>
			<div class="form-group">
				<label for="email">Correo electronico</label> <input name="email"
					class="form-control" value="" />
			</div>
			<div class="form-group">
				<label for="sexo">Sexo</label> <select id="sexo" name="sexo"
					class="form-control">
					<option id="MASCULINO" value="MASCULINO">Masculino</option>
					<option id="FEMENINO" value="FEMENINO">Femenino</option>
				</select>
			</div>
			<div class="form-group">
				<label for="role">Tipo de usuario</label> <select id="role"
					name="role" class="form-control">
					<option id="ROLE_USUARIO" value="ROLE_USUARIO">Usuario</option>
					<option id="ROLE_ADMINISTRADOR" value="ROLE_ADMINISTRADOR">Administrador</option>
					<option id="NO_ACTIVO" value="NO_ACTIVO">No Activo</option>
				</select>
			</div>
			<input class="btn btn-primary pull-right" type="submit"
				value="Crear" />
		</form>

		<footer class="footer">
		<div class="container">
			<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
		</div>
		</footer>
</body>
</html>