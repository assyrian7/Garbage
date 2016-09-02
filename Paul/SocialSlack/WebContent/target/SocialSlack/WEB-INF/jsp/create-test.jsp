<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"></link> <!-- load bootstrap css -->
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"></link> <!-- load fontawesome -->
		<link rel="stylesheet" href=<c:url value="/resources/css/icono.css" />></link>
		<link rel="shortcut icon" href=<c:url value="/resources/images/logo.png" />></link>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
		<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<script src=<c:url value="/resources/javascript/Question.js" />></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
		<title>Create Test</title>
		<script type="text/javascript">
			var qNum = 1;
			var size = 0;
			var question = document.getElementById("question");
			
			function add(){
				var form = document.getElementById("form");
				var cloneQuestion = document.getElementById("question").cloneNode(true);
				form.appendChild(cloneQuestion);
				var minusButton = cloneQuestion.getElementsByTagName("div")[0].firstChild;
				minusButton.id = "minusButton" + qNum;
				cloneQuestion.id = "question" + qNum;
				cloneQuestion.style.display = 'block';
				button.style.display = "block";
				qNum+=1;
				size+=1;
			}
			function minus(questionID){
				var button = document.getElementById("button");
				var ID = parseInt(questionID.substring(8));
				var form = document.getElementById("form");
				var question = document.getElementById("question" + ID);
				question.parentNode.removeChild(question);
				if(size <= 1){
					button.style.display = "none";
				}
				size-=1;
			}
			function submit(){
				var data = "";
				data += "{\"questions\":[";
				var form = document.getElementById("form");
				for(i = 1; i <= size; i++){
					var question = form.getElementsByClassName("container")[i];
					var questionID = i
					var actualQuestion = question.getElementsByClassName("form-group")[0].getElementsByClassName("form-control")[0].value;
					var questionType = question.getElementsByClassName("form-group")[1].getElementsByClassName("form-control")[0].value;
					var possibleAnswers = question.getElementsByClassName("form-group")[2].getElementsByClassName("form-control")[0].value;
					var correctAnswers = question.getElementsByClassName("form-group")[3].getElementsByClassName("form-control")[0].value;
					var questionData = new Question(questionID, actualQuestion, questionType, possibleAnswers, correctAnswers);
					data += questionData.JSON();
					if(i < size){
						data += ",";
					}
				}
				data += "]}";
				console.log(data);
				console.log(size);
				var popup = document.getElementById("popup");
				var response = document.getElementById("testresponse");
				document.getElementById("button").onClick = null;
				document.getElementById("submitButton").disabled = true;
				$.ajax({
					type: 'POST',
		            url : 'http://localhost:8080/SocialSlack/ajax/submit-created-test?testData=' + data + '&testSize=' + size,
		            beforeSend : function(){
		            	openNav();
		            },
		            success : function(data) {
		            	response.innerHtml = data;
		            	console.log(data);
		            	console.log("done");
		            }
		        });
			}
		</script>
		<style>
			#add{
				margin-top: 2%;
				background-color: #2692FF;
				color: #ffffff;
				margin-left: 20%;
				border: none;
				outline: none;
				border-radius: 10px;
				transition: 0.5s;
			}
			#minusButton{
				background-color: #FF3728;
				color: #ffffff;
				border: none;
				outline: none;
				display: inline-block;
				border-radius: 10px;
				transition: 0.5s;
			}
			#addplus{
				color: #ffffff;
				transition: 0.5s
			}
			#minus{
				color: #ffffff;
				transition: 0.5s;
			}
			#topElement{
				display: inline;
			}
			#add:hover{
				background-color: #ffffff;
			}
			#add:hover #addplus{
				color: #2692FF;
			}
			#minusButton:hover{
				background-color: #ffffff;
			}
			#minusButton:hover #minus{
				color: #FF3728;
			}
			#form, #button{
		
				text-align: left;
			
			}
			.form-group, #button{
				width: 60%;
				margin: auto;
			}
			#button{
				outline: none;
			}
			.overlay {
				height: 50%;
				width: 50%;
				margin-left: 25%;
				margin-top: 10%;
				border-radius: 40px;
				position: fixed;
				z-index: 1;
				background-color: rgb(0,0,0);
				background-color: rgba(0,0,0, 0.9);
				overflow-x: hidden;
				transition: 0.5s;
			}

			.overlay-content {
				position: relative;
				top: 25%;
				width: 100%;
				text-align: center;
				margin-top: 30px;
			}

			.overlay a, .overlay p {
				padding: 8px;
				text-decoration: none;
				font-size: 36px;
				color: #818181;
				display: block;
				transition: 0.3s;
			}

			.overlay a:hover, .overlay a:focus, .overlay p:hover, .overlay p:focus{
				color: #f1f1f1;
			}

			.closebtn {
				position: absolute;
				right: 20px;
				font-size: 60px !important;
			}
			
		</style>
	</head>
	<body>
		<div id="popup" class="overlay" style="display: none;">
			<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">x</a>
			<div class="overlay-content">
				<p id="testresponse">Submitting test...</p>
			</div>
		</div>
		<div align="center" id="form">
				<button id="add" onClick="add()">
					<span id="addplus" class="icono-plus"></span>
				</button>
				<div class="container" id="question" style="display: none;">
					<br/>
					<div class="form-group">
						<button id="minusButton" onClick="minus(this.parentNode.parentNode.id)">
							<span id="minus" class="fa fa-minus"></span>
						</button>
						<label>Question</label>
						<input type="text" class="form-control" name="actualQuestion"></input>
					</div>
					<div class="form-group">
						<label>QuestionType</label>
						<select class="form-control" name="type">
							<option value="MC">Multiple Choice</option>
							<option value="FITB">Fill in the blank(s)</option>
							<option value="TF">True/False</option>
						</select>
					</div>
					<div class="form-group">
						<label>Possible Answer(s)</label>
						<input type="text" class="form-control" name="possibleAnswers"></input>
					</div>
					<div class="form-group">
						<label>Correct Answer(s)</label>
						<input type="text" class="form-control" name="correctAnswers"></input>
					</div>
				</div>
				
		</div>
		<div id="button" onClick="submit()" style="display: none;">
			<button id="submitButton" class="btn btn-large btn-primary">Submit</button>
		</div>
		<!--
		<form align="center">
			<input type="hidden" name="testData"></input>
			<input type="hidden" name="testSize"></input>
			<div id="button" style="display: none;">
				<input class="btn btn-large btn-primary" onClick="submit()" type="Submit" value="Submit"></input>
			</div>
		</form>
		-->
		<script>
		<%--
		function grayButton(button){
			button.style.background-color = "#808080";
		}
		--%>
		function openNav() {
			document.getElementById("popup").style.display = "block";
		}
	
		function closeNav() {
			document.getElementById("popup").style.display = "none";
		}
		</script>
	</body>
</html>