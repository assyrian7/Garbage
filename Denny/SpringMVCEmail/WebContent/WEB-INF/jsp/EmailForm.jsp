<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Forgot Password</title>
</head>
<body>
	<h2>Reset your password</h2>
	${error}
	<form:form action="send" method="post" modelAttribute="mail">
		<table border="0" width="35%" align="center"> 
			<div>Enter your email address:</div>
			<form:input path="to" />
			<hr>
			<input type="submit" value="Reset Password">
		</table>
	</form:form>
</body>
</html>