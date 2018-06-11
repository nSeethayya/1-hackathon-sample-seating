<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Seating application</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script src="/js/jquery-2.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container" style="margin: 50px">
		<h3>Seating application Login</h3>
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

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <br /> <a href="/registration"
				style="color: #4285f4; line-height: 1.4286; cursor: pointer;">New
				User</a>
		</form>
	</div>
</body>
</html>