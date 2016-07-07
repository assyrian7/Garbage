<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Paypal Practice</title>
<script src="https://js.braintreegateway.com/js/braintree-2.25.0.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.20/require.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css">
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>	
</head>
<body>
	
	<form id="merchant-form" action="/PaypalPrac/checkout" method="post">
		<div id='dropin-container'></div>
		<select id="method_type" name="pay">
			<option value="fake-valid-nonce">Default</option>
			<option value="fake-valid-visa-nonce">Visa</option>
		</select>
		<input id="type" type="hidden" name="payment_method_nonce"></input>
		<input type="submit" value="Submit"></input>
	</form>
	
	
	<script type="text/javascript">
	
	var clientToken = "";
	
	$.ajax({
		type: 'POST',
		url : '/PaypalPrac/client-token',
		success : function(data) {
			console.log(data);
			clientToken = data;
			console.log(clientToken);
		}
	});
	
	
	braintree.setup("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJlZDM3NzExMTk5MWNmZmE3MzhjODU1NGZiOThkNTA0MTgwOWMyOTRkOWQ0YmU4YWY2OTRmODM1NGQzY2U0MzQ3fGNsaWVudF9pZD1jbGllbnRfaWQkcHJvZHVjdGlvbiRkdHh0bWgzeGhxeHoyOTU3XHUwMDI2Y3JlYXRlZF9hdD0yMDE2LTA2LTI5VDE3OjA4OjA5LjEzMjcxNzA1MCswMDAwXHUwMDI2bWVyY2hhbnRfaWQ9N25ydmpxcDRkdHh6a3NjNCIsImNvbmZpZ1VybCI6Imh0dHBzOi8vYXBpLmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvN25ydmpxcDRkdHh6a3NjNC9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJwcm9kdWN0aW9uIiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy83bnJ2anFwNGR0eHprc2M0L2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5icmFpbnRyZWVnYXRld2F5LmNvbS83bnJ2anFwNGR0eHprc2M0In0sInRocmVlRFNlY3VyZUVuYWJsZWQiOmZhbHNlLCJwYXlwYWxFbmFibGVkIjp0cnVlLCJwYXlwYWwiOnsiZGlzcGxheU5hbWUiOiJwcmFzaGFudHBAa2xvdWRkYXRhLmNvbSIsImNsaWVudElkIjoiQVZjRkFuT1g0RktYN3ViQm93bmo0MlVheDlwOXJLUktxdEhXWnlxWFFKMjc2Z1U3NmN4VmJ3QU1POWROY0twdEJ3TjZaZVlIYklTbXZObXEiLCJwcml2YWN5VXJsIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSIsInVzZXJBZ3JlZW1lbnRVcmwiOiJodHRwczovL2V4YW1wbGUuY29tIiwiYmFzZVVybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9jaGVja291dC5wYXlwYWwuY29tIiwiZGlyZWN0QmFzZVVybCI6bnVsbCwiYWxsb3dIdHRwIjpmYWxzZSwiZW52aXJvbm1lbnROb05ldHdvcmsiOmZhbHNlLCJlbnZpcm9ubWVudCI6ImxpdmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJBUktyWVJEaDNBR1hEelc3c09fM2JTa3EtVTFDN0hHX3VXTkMtejU3TGpZU0ROVU9TYU90SWE5cTZWcFciLCJiaWxsaW5nQWdyZWVtZW50c0VuYWJsZWQiOnRydWUsIm1lcmNoYW50QWNjb3VudElkIjoiVVNEIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sImNvaW5iYXNlRW5hYmxlZCI6ZmFsc2UsIm1lcmNoYW50SWQiOiI3bnJ2anFwNGR0eHprc2M0IiwidmVubW8iOiJvZmYifQ==", "paypal", {
		
		
		paypal: {
			container: 'dropin-container',
			id: 'merchant-form',
	
			singleUse: true, // Required
			amount: 10.00, // Required
			currency: 'USD', // Required
			//singleUse: false, // Required
			billingAgreementDescription: 'You and me',
	        locale: 'en_us',
	        enableShippingAddress: true,
	        /*
	        shippingAddressOverride: {
	            recipientName: 'Prashant Parekh',
	            streetAddress: '44249 Hunter Place',
	            locality: 'Fremont',
	            countryCodeAlpha2: 'US',
	            postalCode: '94539',
	            region: 'CA',
	            phone: '510.468.2739',
	            editable: false
	        }
			*/
	    },
		dataCollector: {
	        paypal: true
	    },
	    onReady: function (braintreeInstance) {
	        var form = document.getElementById('merchant-form');
	        var deviceDataInput = form['device_data'];

	        if (deviceDataInput == null) {
	          deviceDataInput = document.createElement('input');
	          deviceDataInput.name = 'device_data';
	          deviceDataInput.type = 'hidden';
	          form.appendChild(deviceDataInput);
	        }

	        deviceDataInput.value = braintreeInstance.deviceData;
	    },
		onPaymentMethodReceived: function (obj) {
			var method = document.getElementById("method_type");
			var input = document.getElementById("type");
			console.log(obj.nonce);
			input.value = obj.nonce;
		}
	    
		
	});
	</script>
	

</body>
</html>