<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@	taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"> <!-- load fontawesome -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	
	<form:form method="POST" commandName="loginForm">
		<div class="form-group">
			<label>Username</label>
			<form:input type="text" class="form-control" path="username"/>
			<form:errors class="alert alert-danger" path="username"/>
		</div>
		<div class="form-group">
			<label>Password</label>
			<form:input type="password" class="form-control" path="password"/>
			<form:errors class="alert alert-danger" path="password"/>
		</div>
		<input type="submit" name="submit" value="Submit" class="btn btn-warning btn-lg" />
		<c:if test="${not empty result}">
			<div class="alert alert-danger">${result}</div>
		</c:if>
	</form:form>
	
</body>
</html>