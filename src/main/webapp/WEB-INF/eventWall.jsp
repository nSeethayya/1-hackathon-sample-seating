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

	<h2>
		<span>${event.name}</span>
	</h2>
	<div style="width: 50%; float: left">
		Host: <span>${event.userHosted}</span> <br /> Date: <span>${event.eventDate}</span>
		<br />Location: <span>${event.location}, ${event.state}</span> <br />People
		who are attending this event: <span>${event.joinedUsers.size()}</span>
		<br />

		<table class="table table-bordered"
			style="border: 3px solid: #ddd !important;">
			<thead>
				<tr>
					<th>Name</th>
					<th>Location</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${event.joinedUsers}" var="user">
					<tr>
						<td>${user.name}</td>
						<td>${user.location}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div style="width: 50%; float: left">
		<div style="max-width: 330px; padding: 15px; margin: 0 auto;">
			<h2>
				<span>Message Wall</span>
			</h2>
			<div>
				<textarea rows="10" cols="60" disabled>${event.messages}</textarea>
			</div>
		</div>
		<form:form method="POST" action="/events/addMessages/${event.eventId}"
			modelAttribute="messageForm" class="form-signin">
		Add comment:
			<form:textarea path="message" rows="5" cols="30" />
			<br />
			<form:errors style="color: red" path="message"></form:errors>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
		</form:form>
	</div>
	<a style="margin-left: 30px;" href="/events">Back to events</a>

</body>
</html>
