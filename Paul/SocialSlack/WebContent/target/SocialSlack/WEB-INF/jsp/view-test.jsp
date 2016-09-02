<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.peerbuds.SocialSlack.Source.Profile" %>
<%@ page import="com.peerbuds.SocialSlack.Source.Test" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"></link> <!-- load bootstrap css -->
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"></link> <!-- load fontawesome -->
		<link rel="stylesheet" href=<c:url value="/resources/css/icono.css" />></link>
		<link rel="shortcut icon" href=<c:url value="/resources/images/logo.png" />></link>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
		<title>View Test</title>
		<style>
		
			#testButton{
				display: block;
				align: right;
				color: #ffffff;
				text-align: right;
				margin-right: 5%;
				background-color: #1E9DFF;
			}
			#testButton a{
				color: #ffffff;
			}
		
		</style>
	</head>
	<body>
		<%
			Profile profile = (Profile)session.getAttribute("profile");
			Test test = (Test)request.getAttribute("test");
			if(profile != null && profile.getId() == test.getUserID()){
		%>
				<button id="testButton">Edit Test</button>
		<%  } 
			else{
		%>
				<button id="testButton"><a href="?action=take_test">Take Test</a></button>
		<%  } %>
		<p>TestID: ${test.getTestID()}</p>
		<p>UserID: ${test.getUserID()}</p>
		<p># of Questions: ${test.getNumberOfQuestions()}</p>
		<table border=1>
			<thead>
				<tr>
					<th>Question ID</th>
					<th>Test ID</th>
					<th>Question</th>
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
						<td><c:out value="${question.getQuestion()}" /></td>
						<td><c:out value="${question.getQuestionType()}" /></td>
						<td><c:out value="${question.getPossibleAnswers()}" /></td>
						<td><c:out value="${question.getCorrectAnswers()}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>