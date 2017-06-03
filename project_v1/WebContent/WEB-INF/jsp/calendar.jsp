<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link
	href='${pageContext.request.contextPath}/scheduler/lib/fullcalendar.min.css'
	rel='stylesheet' />
<link
	href='${pageContext.request.contextPath}/scheduler/lib/fullcalendar.print.min.css'
	rel='stylesheet' media='print' />
<link
	href='${pageContext.request.contextPath}/scheduler/scheduler.min.css'
	rel='stylesheet' />

<script
	src='${pageContext.request.contextPath}/scheduler/lib/moment.min.js'></script>
<script
	src='${pageContext.request.contextPath}/scheduler/lib/jquery.min.js'></script>
<script
	src='${pageContext.request.contextPath}/scheduler/lib/fullcalendar.min.js'></script>
<script
	src='${pageContext.request.contextPath}/scheduler/scheduler.min.js'></script>
<script
	src='${pageContext.request.contextPath}/fullcalendar/locale-all.js'></script>

<script>
	$(document)
			.ready(
					function() {

						var initialLocaleCode = 'es';
						//	document ready

						$('#calendar')
								.fullCalendar(
										{

											schedulerLicenseKey : 'CC-Attribution-NonCommercial-NoDerivatives',
											eventSources : [

											// your event source
											{
												url : '${pageContext.request.contextPath}/calendar/jsonEvents', // use the `url` property
												color : 'yellow', // an option!
												textColor : 'black' // an option!
											} ],
											locale : initialLocaleCode,
											editable : true, // enable draggable events
											aspectRatio : 1.8,
											scrollTime : '09:00', // undo default 6am scrollTime
											defaultView : 'agendaDay',
											editable : true,
											selectable : true,
											eventLimit : true, // allow "more" link when too many events
											allDaySlot : false,
											slotLabelFormat : 'HH:mm', //9:00
											slotDuration : '00:15:00', //15 min slot duration
											header : {
												left : 'today prev,next',
												center : 'title',
												right : 'agendaDay,agendaThreeDays,agendaWeek,month,listWeek'
											},
											navLinks : true, // can click day/week names to navigate views
											views : {
												agendaThreeDays : {
													type : 'agenda',
													duration : {
														days : 3
													},
													columnFormat : 'ddd D/M',

													// views that are more than a day will NOT do this behavior by default
													// so, we need to explicitly enable it
													groupByResource : true

												//// uncomment this line to group by day FIRST with resources underneath
												//groupByDateAndResource: true
												}
											},

											/* businessHours: {
											    // days of week. an array of zero-based day of week integers (0=Sunday)
											    dow: [ 1, 2, 3, 4 ] // Monday - Thursday

											    start: '10:00', // a start time (10am in this example)
											    end: '18:00', // an end time (6pm in this example)
											}, */

											eventDurationEditable : false,
											resourceLabelText : 'Salas',
											resources : [ {
												id : 'CR',
												title : 'Rayos X',
												eventColor : 'red'
											}, {
												id : 'MR',
												title : 'Resonancia Magnetica',
												eventColor : 'green'
											}, {
												id : 'US',
												title : 'Ecografia',
												eventColor : 'orange'
											}, {
												id : 'CT',
												title : 'Escaner',
												eventColor : 'blue'
											} ],

											/* dayClick: function() {
											    alert('a day has been clicked!');
											} */

											select : function(start, end,
													jsEvent, view, resource) {
												location.href = "${pageContext.request.contextPath}/nuevaCita/"
														+ start.format()
														+ "/"
														+ end.format()
														+ "/"
														+ resource.id;
												/* console.log(
													'select',
													start.format(),
													end.format(),
													resource ? resource.id : '(no resource)'
												);
												alert("Hora inicio "+start.format()+" Hora fin "+end.format()); */
											},
											dayClick : function(date, jsEvent,
													view, resource) {

												/* console.log(
													'dayClick',
													date.format(),
													resource ? resource.id : '(no resource)'
												);
												alert(date.toLocaleString()); */
											},
											eventDrop : function(event,
													dayDelta, minuteDelta,
													allDay, revertFunc) {

												if (!confirm("Are you sure about this change?")) {
													revertFunc();
												} else {
													//AJAX que modifica los datos del estudio con los nuevos datos del evento.												
													$
															.ajax({
																url : "${pageContext.request.contextPath}/calendar/cambiaCita",
																type : "post",
																data : "idstudy="
																		+ event.idstudy
																		+ "&startTime="
																		+ event.start
																				.format()
																		+ "&endTime="
																		+ event.end
																				.format()
																		+ "&modality="
																		+ event.resourceId,

																success : function(
																		response) {
																	alert(response);
																},

																error : function(
																		error) {
																	alert(error);
																}
															});
												}

											},
											eventClick : function(calEvent,
													jsEvent, view) {

												//accedemos a una nueva vista con los datos del estudio donde los podremos cambiar.
												location.href = "${pageContext.request.contextPath}/modificaCita/"
														+ calEvent.idstudy;
												/* alert('Event: '
														+ calEvent.title);
												alert('Coordinates: '
														+ jsEvent.pageX + ','
														+ jsEvent.pageY);
												alert('View: ' + view.name);

												// change the border color just for fun
												$(this).css('border-color',
														'red'); */

											}
										});

					});
</script>

<script>
	function hideErrorMessages(elements) {
		elements = elements.length ? elements : [ elements ];
		if ('${messageError}' == "") {
			for (var index = 0; index < elements.length; index++) {
				elements[index].style.display = 'none';
			}
		}
	}
</script>
<script>
	function hideSuccessMessages(elements) {
		elements = elements.length ? elements : [ elements ];
	}
	if ('${messageSuccess}' == "") {
		for (var index = 0; index < elements.length; index++) {
			elements[index].style.display = 'none';
		}
	}
</script>


<style>
.cuerpo {
	margin: 100px auto;
	padding: auto;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

.fc-header {
	position: fixed;
}

.fc-first .fc-last {
	position: fixed;
}

#calendar {
	max-width: 1000px;
	margin: auto auto;
}
</style>

<title>Calendario</title>
</head>
<body
	onload="hideErrorMessages(document.querySelectorAll('.alert-danger'));hideSuccessMessages(document.querySelectorAll('.alert-success'));">
	<nav id="header" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home">INICIO</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a
					href="${pageContext.request.contextPath}/calendar">Calendario</a></li>
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

	<div id="cuerpo" class="section">
		<div class="container">
			<div class="alert alert-success" role="alert">${messageSuccess}</div>
			<div class="alert alert-danger" role="alert">${messageError}</div>
			<div id="calendar"></div>
		</div>
	</div>

	<footer class="footer" style="margin-top: 10px;">
	<div class="container">
		<p class="text-muted">&copy; 2017 Jose Antonio Molins</p>
	</div>
	</footer>


</body>
</html>