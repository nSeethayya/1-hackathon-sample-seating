<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="en">
<head>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/common.css" rel="stylesheet">
<script src="/js/jquery-2.2.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

</head>

<body>

	<div class="container">
		Select Seat:
		<select id="seats" name='seat' multiple="true"
			onchange="myFunction()">
			<c:forEach items="${seats}" var="seat">
				<option value="${seat.id}">${seat.name}</option>
			</c:forEach>
		</select>
	</div>
</body>
</html>