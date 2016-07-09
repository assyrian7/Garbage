<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://js.braintreegateway.com/js/braintree-2.25.0.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.20/require.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>	
<title>Insert title here</title>
</head>
<body>
	
	<input style = "display: none;" type="hidden" id="src">${link}</input>
	
	<a id = "srr" class = "btn btn-primary" href = "https://www.sandbox.paypal.com/webapps/auth/protocol/openidconnect/v1/authorize?client_id=AejQKaW2cxtS_y0wTH5xt9LfqY0-eugZSYWteLOsPekHx-3KAcub2LaflTG6J70XkigC55c9liI0ot3S&response_type=code&scope=openid+email+address+profile+&redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2FPaypalPrac%2Fmake-paypal-payment">Checkout</a>
	
	
	
</body>
</html>