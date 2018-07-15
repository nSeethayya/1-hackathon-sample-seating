<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Events App</title>
<!-- <link href="/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="/css/common.css" rel="stylesheet">
<!-- <script src="/js/jquery-2.2.1.min.js"></script> -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="/js/seating-handler.js"></script>
</head>
<body>
	<div class="container">
		<h2>
			Welcome, <label style="color: blue;"> ${username}</label>
		</h2>
		<h6>
			<label id="message" style="color: blue;"></label>
		</h6>
		<div class="pull-right">
			<ul class="nav navbar-nav">
				<form method="POST" action="/logout">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<li><button type="submit" class="btn navbar-btn btn-danger"
							id="logout">LogOut</button></li>
				</form>
			</ul>
		</div>

		<ul class="nav nav-tabs">
			<li><a data-toggle="tab" href="#events">Events</a></li>
		</ul>
		<div class="tab-content">

			<div id="events" class="tab-pane fade in active">
				<h3>Here are some of the events in your state</h3>
				<div class="table-responsive">
					<c:if test="${not empty sameStateEvents}">
						<table class="table table-bordered"
							style="border: 3px solid: #ddd !important;">
							<thead>
								<tr>
									<th>Name</th>
									<th>Date</th>
									<th>Location</th>
									<th>Host</th>
									<th>Action / Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${sameStateEvents}" var="event"
									varStatus="myIndex">
									<tr>
										<td><a href="/events/${event.eventId}">${event.name}</a></td>
										<td>${event.eventDate}</td>
										<td>${event.location}</td>
										<td>${event.userHosted}</td>
										<td><c:if test="${event.host}">
												<a href="/events/${event.eventId}/edit">Edit</a>
												<a href="/events/delete/${event.eventId}">Delete</a>
											</c:if> <c:if test="${!event.host && event.joined}">
													Joining <a href="/events/cancel/${event.eventId}">Cancel</a>
											</c:if> <c:if test="${!event.joined}">
												<a href="/events/join/${event.eventId}">Join</a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty sameStateEvents}">
						<h4 style="color: red;">No same State Events</h4>
					</c:if>
				</div>
				<h3>Here are some of the events in other states</h3>
				<div class="table-responsive">
					<c:if test="${not empty otherStateEvents}">
						<table class="table table-bordered"
							style="border: 3px solid: #ddd !important;">
							<thead>
								<tr>
									<th>Name</th>
									<th>Date</th>
									<th>Location</th>
									<th>Host</th>
									<th>Action / Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${otherStateEvents}" var="event"
									varStatus="myIndex">
									<tr>
										<td><a href="/events/${event.eventId}">${event.name}</a></td>
										<td>${event.eventDate}</td>
										<td>${event.location}</td>
										<td>${event.userHosted}</td>
										<td><c:if test="${event.host}">
												<a href="/events/${event.eventId}/edit">Edit</a>
												<a href="/events/delete/${event.eventId}">Delete</a>
											</c:if> <c:if test="${!event.host && event.joined}">
													Joining <a href="/events/cancel/${event.eventId}">Cancel</a>
											</c:if> <c:if test="${!event.joined}">
												<a href="/events/join/${event.eventId}">Join</a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty otherStateEvents}">
						<h4 style="color: red;">No other State Events</h4>
					</c:if>
				</div>

			</div>


			<h2>Create an Event</h2>
			<form:form method="POST" modelAttribute="eventForm"
				action="/events/create" class="form-signin"
				style="margin-left: 15px;">

				<form:input type="text" path="name" class="form-control"
					placeholder="Name" autofocus="true"></form:input>
				<br />
				<form:errors style="color: red" path="name"></form:errors>

				<form:input type="date" path="createdAt" />
				<br />
				<form:errors style="color: red" path="createdAt"></form:errors>

				<form:input type="text" path="location" class="form-control"
					placeholder="Location" autofocus="true"></form:input>
				<br />
				<form:errors style="color: red" path="location"></form:errors>

				<form:select path="state" items="${states}" />
				<br />
				<form:errors style="color: red" path="state"></form:errors>

				<button class="btn btn-lg btn-primary btn-block" type="submit">Create</button>

			</form:form>
		</div>
</body>
</html>
