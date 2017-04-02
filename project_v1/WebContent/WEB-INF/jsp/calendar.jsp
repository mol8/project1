<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
				location.href = "${pageContext.request.contextPath}/nuevaCita/"+start.format()+"/12/13";
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
		margin: 20px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 1000px;
		margin: 0 auto;
	}

</style>

<title>Calendario</title>
</head>
<body>

<a href="${pageContext.request.contextPath}/logout">Logout</a>

<a href="${pageContext.request.contextPath}/home">Home</a>

	<div id='calendar'></div>

</body>
</html>