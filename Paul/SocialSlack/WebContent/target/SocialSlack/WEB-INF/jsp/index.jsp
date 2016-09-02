<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PbCorpHub</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="submit-profile.js"></script>
</head>
<body>
	
	  
	<form action="/SocialSlack/" method="POST">
		<input type="hidden" name="action" value="getTest"></input>
            Please enter your TestID:  <input type="text" name="testID" size="20px"> <br>
        <input type="submit" value="submit">
    </form>
    
    
    
    <form action="/SocialSlack/" method="POST">
    	<input type="hidden" name="action" value="postProfile"></input>
    	Username:<input type="text" name="username"></input>
    	Password:<input type="text" name="password"></input>
    	<input type="submit" value="Submit"></input>
    </form>
    
    
    <!-- 
    <form action="MainServlet" method="POST">
    	<input type="hidden" name="testID" value="1"></input>
    	<input type="hidden" name="action" value="postQuestion"></input>
    	<div>
    		<p>1. What Country are we in?</p>
    		<input type="hidden" name="questionID" value="1"></input>
    		<input type="radio" name="answer" value="USA"></input>USA
    		<input type="radio" name="answer" value="Germany"></input>Germany
    		<input type="radio" name="answer" value="England"></input>England
    		<input type="radio" name="answer" value="Italy"></input>Italy
    		
    		<input type="hidden" name="unchecked" value="USA"></input>
    		<input type="hidden" name="unchecked" value="Germany"></input>
    		<input type="hidden" name="unchecked" value="Italy"></input>
    		<input type="hidden" name="unchecked" value="England"></input>

    	</div>
    	
    	<input type="submit" value="Submit"></input>
    </form>
    -->
    
    
    <!--  
    Enter your username: <input type="text" id="username"></input><br/>
    Enter your password: <input type="text" id="password"></input><br/>
    <button value="Submit" onClick="submit-profile()">Sign Up</button>
    <br></br>
    <br></br>
    
    <p id="res">Hello There</p>
	-->

</body>
</html>