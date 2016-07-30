<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World</title>
</head>
<body>
	<h2>Send Email Success</h2>
	<ul>
		<li>From: ${mail.from}</li>
		<li>To: ${mail.to}</li>
		<li>Subject: ${mail.subject}</li>
		<li>Body: ${mail.body}</li>
		</li>
	</ul>
</body>
</html>