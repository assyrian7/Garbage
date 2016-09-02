<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"> <!-- load fontawesome -->
	<link rel="shortcut icon" href=<c:url value="/resources/images/logo.png" />></link>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Dashboard</title>
	
	<style>
	
		#content{
			width: 100%;
			display: table;
		}
		#main-content{
			display: table-cell;
			text-align:left
		}
		#links{
			display: table-cell;
			vertical-align: right;
			text-align: left;
		}
	
	</style>
</head>
<body>
	
	<div id="wrapper">
		<div id="header">
		
		</div>
		<div id="content">
			<div id="main-content">
				<p>ID: ${profile.getId()}</p>
				<p>Firstname: ${profile.getFirstname()}</p>
				<p>Lastname: ${profile.getLastname()}</p>
				<p>Username: ${profile.getUsername()}</p>
				<p>Password: ${profile.getPassword()}</p>
				<p>Email: ${profile.getEmail()}</p>
			</div>
			<div id="links">
				<a href="http://localhost:8080/SocialSlack/test/my-tests">Your Tests</a>
				<a class="btn btn-primary" href="logout">Logout</a>	
			</div>
		</div>
		<div id="footer">
		
		</div>
	</div>

</body>
</html>