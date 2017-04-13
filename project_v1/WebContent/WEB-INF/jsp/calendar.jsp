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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<link href='${pageContext.request.contextPath}/scheduler/lib/fullcalendar.min.css' rel='stylesheet' />
<link href='${pageContext.request.contextPath}/scheduler/lib/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<link href='${pageContext.request.contextPath}/scheduler/scheduler.min.css' rel='stylesheet' />

<script src='${pageContext.request.contextPath}/scheduler/lib/moment.min.js'></script>
<script src='${pageContext.request.contextPath}/scheduler/lib/jquery.min.js'></script>
<script src='${pageContext.request.contextPath}/scheduler/lib/fullcalendar.min.js'></script>
<script src='${pageContext.request.contextPath}/scheduler/scheduler.min.js'></script>
<script src='${pageContext.request.contextPath}/fullcalendar/locale-all.js'></script>

<script>

	$(document).ready(function() {

		var initialLocaleCode = 'es';
	//	document ready

		$('#calendar').fullCalendar({

			schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
			eventSources: [

		        // your event source
		        {
		            url: '${pageContext.request.contextPath}/calendar/jsonEvents', // use the `url` property
		            color: 'yellow',    // an option!
		            textColor: 'black'  // an option!
		        }
		    ],
			locale: initialLocaleCode,
			editable: true, // enable draggable events
			aspectRatio: 1.8,
			scrollTime: '09:00', // undo default 6am scrollTime
			defaultView: 'agendaDay',
			editable: true,
			selectable: true,
			eventLimit: true, // allow "more" link when too many events
			allDaySlot: false,
			slotLabelFormat:'HH:mm', //9:00
			slotDuration: '00:15:00', //15 min slot duration
			header: {
				left: 'today prev,next',
				center: 'title',
				right: 'agendaDay,agendaThreeDays,agendaWeek,month,listWeek'
			},
			navLinks: true, // can click day/week names to navigate views
			views: {
				agendaThreeDays: {
					type: 'agenda',
					duration: { days: 3 },
					columnFormat: 'ddd D/M',

				// views that are more than a day will NOT do this behavior by default
				// so, we need to explicitly enable it
				groupByResource: true
	
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

			
			resourceLabelText: 'Salas',
			resources: [
				{ id: 'CR', title: 'Rayos X', eventColor: 'red' },
				{ id: 'MR', title: 'Resonancia Magnetica', eventColor: 'green' },
				{ id: 'US', title: 'Ecografia', eventColor: 'orange' },
				{ id: 'CT', title: 'Escaner', eventColor: 'blue'}
			],
			events: [
				{ id: '1', resourceId: 'CR', start: '2017-02-07T02:00:00', end: '2017-02-07T07:00:00', title: 'event 1' },
				{ id: '2', resourceId: 'MR', start: '2017-02-07T05:00:00', end: '2017-02-07T22:00:00', title: 'event 2' },
				{ id: '3', resourceId: 'US', start: '2017-02-06', end: '2017-02-08', title: 'event 3' },
				{ id: '4', resourceId: 'CT', start: '2017-02-07T03:00:00', end: '2017-02-07T08:00:00', title: 'event 4' },
				{ id: '5', resourceId: 'MR', start: '2017-02-07T00:30:00', end: '2017-02-07T02:30:00', title: 'event 5' }
			],
			
			/* dayClick: function() {
		        alert('a day has been clicked!');
		    } */

			select: function(start, end, jsEvent, view, resource) {
				location.href = "${pageContext.request.contextPath}/nuevaCita/"+start.format()+"/"+end.format();
				/* console.log(
					'select',
					start.format(),
					end.format(),
					resource ? resource.id : '(no resource)'
				);
				alert("Hora inicio "+start.format()+" Hora fin "+end.format()); */
			},
			dayClick: function(date, jsEvent, view, resource) {
				
				/* console.log(
					'dayClick',
					date.format(),
					resource ? resource.id : '(no resource)'
				);
				alert(date.toLocaleString()); */
			}
		});
	
	});

</script>


<style>
body {
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
<body>
<nav id="header" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Users app</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${pageContext.request.contextPath}/calendar">Calendario</a></li>
				<li><a href="${pageContext.request.contextPath}/userList">Lista
						de usuarios</a></li>
				<li><a href="${pageContext.request.contextPath}/patientList">Lista
						de pacientes</a></li>
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
	
	<div id="body" class="section">
	<div class="content">
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