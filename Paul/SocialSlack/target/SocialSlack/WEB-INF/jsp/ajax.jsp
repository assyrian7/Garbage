<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"> <!-- load fontawesome -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<script type="text/javascript">
	    function crunchifyAjax() {
	        $.ajax({
	            url : 'ajax/randInt',
	            success : function(data) {
	                $('#result').html(data);
	            }
	        });
	    }
	</script>
 
	<script type="text/javascript">
    	var intervalId = 0;
    	
	</script>
</head>
<body>
	<div align="center">
        <br> <br> ${message} <br> <br>
        <button onClick="crunchifyAjax()">Click me!</button>
        <div id="result"></div>
    </div>
</body>
</html>