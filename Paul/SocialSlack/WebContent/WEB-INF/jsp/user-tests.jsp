<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"></link> <!-- load bootstrap css -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"></link> <!-- load fontawesome -->
	<link rel="stylesheet" href=<c:url value="/resources/css/icono.css" />></link>
	<link rel="shortcut icon" href=<c:url value="/resources/images/logo.png" />></link>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Tests</title>
</head>
<body>
	<table border=1>
			<thead>
				<tr>
					<th>Test ID</th>
					<th>User ID</th>
					<th>Number Of Questions</th>
					<th>Points</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tests}" var="test">
					<tr>
						<td><c:out value="${test.getTestID()}" /></td>
						<td><c:out value="${test.getUserID()}" /></td>
						<td><c:out value="${test.getNumberOfQuestions()}" /></td>
						<td><c:out value="${test.getPoints()}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</body>
</html>