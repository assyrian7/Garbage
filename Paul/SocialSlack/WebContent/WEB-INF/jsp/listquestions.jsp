<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question List</title>
</head>
<body>
	<table border=1>
		<thead>
			<tr>
				<th>Question ID</th>
				<th>Test ID</th>
				<th>Question Type</th>
				<th>Possible Answers</th>
				<th>Correct Answer</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questions}" var="question">
				<tr>
					<td><c:out value="${question.getQuestionID()}" /></td>
					<td><c:out value="${question.getTestID()}" /></td>
					<td><c:out value="${question.getQuestionType()}" /></td>
					<td><c:out value="${question.getPossibleAnswers()}" /></td>
					<td><c:out value="${question.getCorrectAnswers()}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script>
	
		var a = ${questions.size()};
		console.log(a);
	
	</script>
</body>
</html>