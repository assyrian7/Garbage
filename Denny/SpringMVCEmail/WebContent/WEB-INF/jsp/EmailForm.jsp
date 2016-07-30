<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email sender</title>
</head>
<body>
	<h2>Spring MVC E-mail</h2>
	${error}
	<form:form action="send" method="post" modelAttribute="mail">
		<table border="0" width="35%" align="center"> 
			<div>To:</div>
			<form:input path="to" />
			<div>Subject:</div>
			<form:input path="subject" />
			<div>Body:</div>
			<form:textarea path="body" rows="10" cols="40" />
			<hr>
			<input type="submit" value="Send">
		</table>
	</form:form>
</body>
</html>