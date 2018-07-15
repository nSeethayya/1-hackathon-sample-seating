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
<title>Booking Seat App</title>
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
			Welcome <label style="color: blue;"> ${username}</label>
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
			<li class="active"><a data-toggle="tab" href="#bookTickets">Book
					Tickets</a></li>
			<li><a data-toggle="tab" href="#bookHistory">Booking History</a></li>
		</ul>
		<div class="tab-content">
			<div id="bookTickets" class="tab-pane fade in active">
				<form:form method="POST" modelAttribute="sectionForm"
					action="/bookTickets" class="form-signin">
					<div class="form-group">
						<div class="row">
							<div class="col-xs-6 col-sm-6 col-md-6">
								<label for="sel1">Select Section:</label>
								<form:select id="sections" name='role' path="sectionId"
									multiple="true" class="form-control">
									<c:forEach items="${sections}" var="section">
										<c:if test="${section.id != selectedSection}">
											<option value="${section.id}">${section.name}</option>
										</c:if>
										<c:if test="${section.id == selectedSection}">
											<option value="${section.id}" selected="selected">${section.name}</option>
										</c:if>
									</c:forEach>
								</form:select>
								<form:errors style="color: red" path="sectionId"></form:errors>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6">
								<label for="sel1">Select Seat:</label>

								<form:select id="seatContainer" path="seatId" multiple="true"
									class="form-control">
								</form:select>
								<form:errors style="color: red" path="seatId"></form:errors>
							</div>
						</div>
					</div>
					<br />
					<button id="bookTickets" class="btn btn-lg btn-primary btn-block"
						type="submit">Book Ticket</button>
				</form:form>
			</div>

			<div id="bookHistory" class="tab-pane fade">
				<h3>Booking History</h3>
				<div class="table-responsive">
					<c:if test="${not empty bookingHistory}">
						<table class="table">
							<thead>
								<tr>
									<th>S.No.#</th>
									<th>Section</th>
									<th>Seat</th>
									<th>Price Per Ticket</th>
									<th>Total Cost</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bookingHistory}" var="bookedSection"
									varStatus="myIndex">
									<tr>
										<td>${myIndex.index+1}</td>
										<td>${bookedSection.sectionName}</td>
										<td>${bookedSection.seatName}</td>
										<td>${bookedSection.pricePerTicket}</td>
										<td>${bookedSection.totalCost}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty bookingHistory}">
						<h4 style="color: red;">No booking history</h4>
					</c:if>
				</div>
			</div>
		</div>
</body>
</html>
