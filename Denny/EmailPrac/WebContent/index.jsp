<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Email</title>
	</head>
	<body>
		<h1>Email</h1>
		<form method="POST" action="EmailServlet">
    		<label for="to">To:</label><input id="to" name="to" type="text"/><br/>
    		<label for="subject">Subject:</label><input id="subject" name="subject" type="text"/><br/>
    		<textarea name="body" cols="60" rows="15"></textarea><br/>
    		<input type="submit" value="Send"/>
		</form>
	</body>
</html>