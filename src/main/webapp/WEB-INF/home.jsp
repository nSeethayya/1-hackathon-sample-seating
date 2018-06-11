<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking Seat App</title>
<script src="/js/jquery-2.2.1.min.js"></script>
<script src="/js/seating-handler.js"></script>
</head>
<body>
	<h1>Welcome User ${username}</h1>
	<form:form method="POST" modelAttribute="sectionForm"
		action="/bookTickets" class="form-signin">
	Select Section:
	<form:select id="sections" name='role' path="sectionId" multiple="true">
			<c:forEach items="${sections}" var="section">
				<option value="${section.id}">${section.name}</option>
			</c:forEach>
		</form:select>

		<!-- <div id="seatContainer"></div> -->
Select seat:
		<form:select id="seatContainer" path="seatId" multiple="true">
		</form:select>
<br/>	
		<button id="bookTickets" class="btn btn-lg btn-primary btn-block"
			type="submit">Book Ticket</button>
	</form:form>

<br/>
	<h2>Booking History</h2>

	<div>
		<c:forEach items="${bookingHistory}" var="bookedSection">
			<span>Section: ${bookedSection.sectionName} Seat:
				${bookedSection.seatName}</span>
			<br />
		</c:forEach>
	</div>

	<div id="messageStatus"></div>

</body>
</html>
