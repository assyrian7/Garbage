<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>	
<title>Insert title here</title>
</head>
<body>
	
	<form action="/PaypalPrac/pay-peerbuds" method="POST">
		<div class="form-group">
			<label>Student Email:</label>
			<input class="form-control" type="text" name="student-email"></input>
		</div>
		<div class="form-group">
			<label>Tutor Email:</label>
			<input class="form-control" type="text" name="tutor-email"></input>
		</div>
		<div class="form-group">
			<label>Amount:</label>
			<input class = "form-control" type="text" name="amount"></input>
		</div>
		<input type="submit"></input>
	</form>
	
</body>
</html>