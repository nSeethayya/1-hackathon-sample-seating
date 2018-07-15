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
<title>Edit Event App</title>
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

	<div style="margin-left: 15px;">
		<h2>
			<span>${eventForm.name}</span>
		</h2>
		<h4>Edit event</h4>
	</div>
	<div style="margin-left: 10px;">
		<form:form method="POST" modelAttribute="eventForm"
			action="/events/edit" class="form-signin">
			<form:input type="text" path="name" class="form-control"
				placeholder="Name" autofocus="true"></form:input>
			<br />
			<form:errors style="color: red" path="name"></form:errors>

			<form:input type="date" path="createdAt" />
			<br />
			<form:errors style="color: red" path="createdAt"></form:errors>
			<form:input type="hidden" path="eventId" />

			<form:input type="text" path="location" class="form-control"
				placeholder="Location" autofocus="true"></form:input>
			<br />
			<form:errors style="color: red" path="location"></form:errors>

			<form:select path="state" items="${states}" />

			<button class="btn btn-lg btn-primary btn-block" type="submit">Edit</button>
			<a href="/events">Cancel</a>
		</form:form>
	</div>
</body>
</html>
