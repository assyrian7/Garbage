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
	<script src=<c:url value="/resources/javascript/QuestionResult.js" />></script>
	
</head>
<body>

	
	
	<div id="mcQuestion" class="container" style="display: none;">
		<p class="question"></p>
		<input class="type" type="hidden" value="MC" />
		<input class="answer" type="radio" name="correctAnswer" value="" /><label>Paul</label><br/>
		<input class="answer" type="radio" name="correctAnswer" value="" /><label>Paul</label><br/>
		<input class="answer" type="radio" name="correctAnswer" value="" /><label>Paul</label><br/>
		<input class="answer" type="radio" name="correctAnswer" value="" /><label>Paul</label><br/>
		
	</div>

	<div id="tfQuestion" class="container" style="display: none;">
		<p class="question"></p>
		<input class="type" type="hidden" value="TF" />
		<input class="answer" type="radio" name="correctAnswer" value="" /><label>True</label><br/>
		<input class="answer" type="radio" name="correctAnswer" value="" /><label>False</label><br/>
	</div>
	
	<div id="fitbQuestion" class="container" style="display: none;">
		<p class="question"></p>
		<input class="type" type="hidden" value="FITB" />
		<div class="possibleAnswers">
		</div>
	</div>
	
	<div id="form"></div>
	
	<div id="button">
		<input class="btn btn-large btn-primary" onClick="submit()" type="Submit" value="Submit"></input>
	</div>
	
	<script>
		
		var mcCounter = 0;
		var tfCounter = 0;
		var fitbCounter = 0;
		var qCounter = 0;
		var questions = [];
		
		
		var questionID;
		var actualQuestion;
		var questionType;
		var possibleAnswers;
		
		var size = ${questions.size()};
		/*
		for(var i = 0; i < size; i++){
			console.log(i);
			questionID = ${questions.get(i).getQuestionID()};
			console.log(questionID);
			question = "${questions.get(i).getQuestion()}";
			console.log(question);
			questionType = "${questions.get(i).getQuestionType()}";
			console.log(questionType);
			possibleAnswers = "${questions.get(i).getPossibleAnswers()}";
			console.log(possibleAnswers);
			questions[i] = new Question(questionID, question, questionType, possibleAnswers, null);
			console.log(questions[i].JSON());
		}
		*/
		
		<c:forEach items="${questions}" var="question">
			questionID = ${question.getQuestionID()};
			actualQuestion = "${question.getQuestion()}";
			questionType = "${question.getQuestionType()}";
			possibleAnswers = "${question.getPossibleAnswers()}";
			questions.push(new Question(questionID, actualQuestion, questionType, possibleAnswers, null));
		</c:forEach>
		
			
		for(var i = 0; i < questions.length; i++){
			console.log(questions[i].JSON());
			if(questions[i].questionType == "MC"){
				var question = questions[i];
				var mcQuestion = document.getElementById("mcQuestion").cloneNode(true);
				var form = document.getElementById("form");
				form.appendChild(mcQuestion);
				mcQuestion.id = "mc" + (mcCounter + 1);
				var possibleAnswers = mcQuestion.getElementsByClassName("answer");
				var pAnswers = question.possibleAnswers.split(';');
				var labels = mcQuestion.getElementsByTagName("label");
				possibleAnswers[0].value = pAnswers[0];
				possibleAnswers[1].value = pAnswers[1];
				possibleAnswers[2].value = pAnswers[2];
				possibleAnswers[3].value = pAnswers[3];
				
				possibleAnswers[0].name = mcQuestion.id + possibleAnswers[0].name;
				possibleAnswers[1].name = mcQuestion.id + possibleAnswers[1].name;
				possibleAnswers[2].name = mcQuestion.id + possibleAnswers[2].name;
				possibleAnswers[3].name = mcQuestion.id + possibleAnswers[3].name;

				
				labels[0].innerHTML = pAnswers[0];
				labels[1].innerHTML = pAnswers[1];
				labels[2].innerHTML = pAnswers[2];
				labels[3].innerHTML = pAnswers[3];

				var q = mcQuestion.getElementsByClassName("question")[0];
				q.innerHTML = question.question;
				mcQuestion.style.display = 'block';
			}
			else if(questions[i].questionType == "TF"){
				var question = questions[i];
				var tfAnswers = question.possibleAnswers.split(';');
				var tfQuestion = document.getElementById("tfQuestion").cloneNode(true);
				var form = document.getElementById("form");
				form.appendChild(tfQuestion);
				tfQuestion.id = "tf" + (tfCounter + 1);
				var possibleAnswers = tfQuestion.getElementsByClassName("answer");
				var labels = tfQuestion.getElementsByTagName("label");
				
				possibleAnswers[0].value = tfAnswers[0];
				possibleAnswers[1].value = tfAnswers[1];
				
				possibleAnswers[0].name = tfQuestion.id + possibleAnswers[0].name;	
				possibleAnswers[1].name = tfQuestion.id + possibleAnswers[1].name;
				
				labels[0].innerHTML = tfAnswers[0];
				labels[1].innerHTML = tfAnswers[1];

				var q = tfQuestion.getElementsByClassName("question")[0];
				q.innerHTML = question.question;
				tfQuestion.style.display = 'block';
			}
			else if(questions[i].questionType == "FITB"){
				var question = questions[i];
				var fitbAnswers = question.possibleAnswers;
				var fitbQuestion = document.getElementById("fitbQuestion").cloneNode(true);
				var form = document.getElementById("form");
				form.appendChild(fitbQuestion);
				fitbQuestion.id = "fitb" + (fitbCounter + 1);
				var possibleAnswers = fitbQuestion.getElementsByClassName("possibleAnswers")[0];
				/*var html = "";
				for(var i = 0; i < fitbpAnswers.length; i++){
					if(fitbpAnswers[i] == '_'){
						html += "<input type=\"text\" class=\"input\" value=\"\"></input>";
					}
					else{
						html += fitbpAnswers[i];
					}
				}
				*/
				var html = fitbAnswers.replace(/___/g, "<input type=\"text\" class=\"input\" value=\"\"></input>");
				possibleAnswers.innerHTML = html;
				var q = fitbQuestion.getElementsByClassName("question")[0];
				q.innerHTML = question.question;
				fitbQuestion.style.display = 'block';
			}
			
		}
		/*
		var testID = 1;
		
		var mcCounter = 0;
		var tfCounter = 0;
		var fitbCounter = 0;
		
		var question = "What is my name?";
		var pAnswers = ["Paul", "David", "Ryan", "Chris"];
		var correctAnswer = "Paul";
		var questionType = "MC";
		
		var tfquestion = "Are we in the US?";
		var tfAnswers = ["T", "F"];
		var tfCorrectAnswer = "T";
		var tfQuestionType = "TF";
		
		var fitbquestion = "Fill in the blanks.";
		var fitbpAnswers = "The dog's owner is paul_BB but the dog's name is ___ and the cat's name is ___.";
		var fitbQuestionType = "FITB";
		//var mcQuestion = document.getElementById("mcQuestion").cloneNode(true);
		
		if(questionType == "MC"){
			var mcQuestion = document.getElementById("mcQuestion").cloneNode(true);
			var form = document.getElementById("form");
			form.appendChild(mcQuestion);
			mcQuestion.id = "mc" + (mcCounter + 1);
			var possibleAnswers = mcQuestion.getElementsByClassName("answer");
			var labels = mcQuestion.getElementsByTagName("label");
			possibleAnswers[0].value = pAnswers[0];
			possibleAnswers[1].value = pAnswers[1];
			possibleAnswers[2].value = pAnswers[2];
			possibleAnswers[3].value = pAnswers[3];
			
			possibleAnswers[0].name = mcQuestion.id + possibleAnswers[0].name;
			possibleAnswers[1].name = mcQuestion.id + possibleAnswers[1].name;
			possibleAnswers[2].name = mcQuestion.id + possibleAnswers[2].name;
			possibleAnswers[3].name = mcQuestion.id + possibleAnswers[3].name;

			
			labels[0].innerHTML = pAnswers[0];
			labels[1].innerHTML = pAnswers[1];
			labels[2].innerHTML = pAnswers[2];
			labels[3].innerHTML = pAnswers[3];

			var q = mcQuestion.getElementsByClassName("question")[0];
			q.innerHTML = question;
			mcQuestion.style.display = 'block';
		}
		if(tfQuestionType == "TF"){
			var tfQuestion = document.getElementById("tfQuestion").cloneNode(true);
			var form = document.getElementById("form");
			form.appendChild(tfQuestion);
			tfQuestion.id = "tf" + (tfCounter + 1);
			var possibleAnswers = tfQuestion.getElementsByClassName("answer");
			var labels = tfQuestion.getElementsByTagName("label");
			
			possibleAnswers[0].value = tfAnswers[0];
			possibleAnswers[1].value = tfAnswers[1];
			
			possibleAnswers[0].name = tfQuestion.id + possibleAnswers[0].name;	
			possibleAnswers[1].name = tfQuestion.id + possibleAnswers[1].name;
			
			labels[0].innerHTML = tfAnswers[0];
			labels[1].innerHTML = tfAnswers[1];

			var q = tfQuestion.getElementsByClassName("question")[0];
			q.innerHTML = tfquestion;
			tfQuestion.style.display = 'block';
		}
		if(fitbQuestionType == "FITB"){
			var fitbQuestion = document.getElementById("fitbQuestion").cloneNode(true);
			var form = document.getElementById("form");
			form.appendChild(fitbQuestion);
			fitbQuestion.id = "fitb" + (fitbCounter + 1);
			var possibleAnswers = fitbQuestion.getElementsByClassName("possibleAnswers")[0];
			/*var html = "";
			for(var i = 0; i < fitbpAnswers.length; i++){
				if(fitbpAnswers[i] == '_'){
					html += "<input type=\"text\" class=\"input\" value=\"\"></input>";
				}
				else{
					html += fitbpAnswers[i];
				}
			}
			
			var html = fitbpAnswers.replace(/___/g, "<input type=\"text\" class=\"input\" value=\"\"></input>");
			possibleAnswers.innerHTML = html;
			var q = fitbQuestion.getElementsByClassName("question")[0];
			q.innerHTML = fitbquestion;
			fitbQuestion.style.display = 'block';
		}
		*/
		
		function submit(){
			var data = "";
			var testID = ${questions.get(0).getTestID()};
			data += "{\"questions\":[";
			var form = document.getElementById("form");
			var questions = form.getElementsByClassName("container");
			for(var i = 0; i < questions.length; i++){
				var question = questions[i];
				var questionID = i + 1;
				var answer = "";
				var questionType = question.getElementsByClassName("type")[0].value;
				if(questionType == "MC" || questionType == "TF"){
					var answers = question.getElementsByClassName("answer");
					for(var j = 0; j < answers.length; j++){
						if(answers[j].checked){
							answer = answers[j].value;
						}
					}
				}
				else if(questionType == "FITB"){
					var inputs = question.getElementsByClassName("possibleAnswers")[0].getElementsByClassName("input");
					for(var j = 0; j < inputs.length; j++){
						answer += inputs[j].value;
						if(j + 1 < inputs.length){
							answer += ";";
						}
					}
				}
				var questionResultData = new QuestionResult(questionID, testID, answer);
				data += questionResultData.JSON();
				if(i + 1 < questions.length){
					data += ",";
				}
			}
			data += "]}";
			console.log(data);
			$.ajax({
				type: 'POST',
	            url : 'http://localhost:8080/SocialSlack/ajax/submit-taken-test?testData=' + data + '&testID=' + testID,
	            success : function(data) {
	            	console.log(data);
	            	console.log("done");
	            }
	        });
		}
		
	</script>
</body>
</html>