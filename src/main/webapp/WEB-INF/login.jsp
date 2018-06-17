<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Seating application</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/common.css" rel="stylesheet">
<script src="/js/jquery-2.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function onload() {
		if (window.location.href.indexOf("logout") != -1) {
			document.getElementById('message').innerHTML = 'Successfully logged out';
		}
	}
</script>
</head>
<body onload="onload()">
	<div class="modal-body row">
		<div class="col-md-6">
			<!--  Registration Start-->
			<div class="container">
				<h6>
					<label id="message" style="color: blue;"></label>
				</h6>
				<form:form method="POST" modelAttribute="userForm"
					action="/registration" class="form-signin">
					<h3 class="form-signin-heading">Create your account</h3>
					<spring:bind path="firstName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="firstName" class="form-control"
								placeholder="First Name" autofocus="true"></form:input>
							<form:errors style="color: red" path="firstName"></form:errors>
						</div>
					</spring:bind>
					<spring:bind path="lastName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="lastName" class="form-control"
								placeholder="Last Name" autofocus="true"></form:input>
							<form:errors style="color: red" path="lastName"></form:errors>
						</div>
					</spring:bind>
					<spring:bind path="email">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="email" class="form-control"
								placeholder="Email" autofocus="true"></form:input>
							<form:errors style="color: red" path="email"></form:errors>
						</div>
					</spring:bind>
					<spring:bind path="location">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="text" path="location" class="form-control"
								placeholder="Location" autofocus="true"></form:input>
							<form:errors style="color: red" path="location"></form:errors>
						</div>
					</spring:bind>

					<form:select path="country" items="${countries}" />

					<spring:bind path="password">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="password" path="password" class="form-control"
								placeholder="Password"></form:input>
							<form:errors style="color: red" path="password"></form:errors>
						</div>
					</spring:bind>
					<spring:bind path="passwordConfirm">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="password" path="passwordConfirm"
								class="form-control" placeholder="Pw Conf"></form:input>
							<form:errors style="color: red" path="passwordConfirm"></form:errors>
						</div>
					</spring:bind>

					<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
				</form:form>
			</div>
			<!--  Registration End -->
		</div>

		<!--  Login Start -->
		<div class="col-md-6">
			<div class="container" style="margin: 50px">
				<h3>Login</h3>
				<c:if test="${param.error ne null}">
					<div style="color: red">Invalid credentials.</div>
				</c:if>
				<form action="/login" method="post">
					<div class="form-group">
						<label for="username">UserName: <input type="text"
							class="form-control" id="username" name="username">
					</div>
					<div class="form-group">
						<label for="pwd">Password:</label> <input type="password"
							style="width: 18%;" class="form-control" id="pwd" name="password">
					</div>
					<button type="submit" class="btn btn-success">Submit</button>
				</form>
			</div>
		</div>
		<!--  Login end-->
	</div>
</body>
</html>