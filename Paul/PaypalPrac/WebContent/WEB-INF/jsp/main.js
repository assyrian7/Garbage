/**
 * 
 */

function getClientToken(){
			
			var clientToken = "";
			
			$.ajax({
				type: 'POST',
				url : '/PaypalPrac/client-token',
				success : function(data) {
					console.log(data);
					return data;
				}
			});
			
			
			
		}
		
		var token = getClientToken();

require.config({
  paths: {
    braintree: 'https://js.braintreegateway.com/js/braintree-2.25.0.min.js'
  }
});

require(['braintree'], function (braintree) {
	braintree.setup('eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI3N2QxZTg1NmYwNWE2MmU5YjVjOWM5MjU0MTc4YjVjYjUyYzk2NDM5YjYyNjBkYTZkZWI5YWVhMGU5ZTMyOWFkfGNsaWVudF9pZD1jbGllbnRfaWQkcHJvZHVjdGlvbiRkdHh0bWgzeGhxeHoyOTU3XHUwMDI2Y3JlYXRlZF9hdD0yMDE2LTA2LTI5VDA0OjA4OjA5Ljk0NTg4MjgzNyswMDAwXHUwMDI2bWVyY2hhbnRfaWQ9N25ydmpxcDRkdHh6a3NjNCIsImNvbmZpZ1VybCI6Imh0dHBzOi8vYXBpLmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvN25ydmpxcDRkdHh6a3NjNC9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJwcm9kdWN0aW9uIiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy83bnJ2anFwNGR0eHprc2M0L2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5icmFpbnRyZWVnYXRld2F5LmNvbS83bnJ2anFwNGR0eHprc2M0In0sInRocmVlRFNlY3VyZUVuYWJsZWQiOmZhbHNlLCJwYXlwYWxFbmFibGVkIjp0cnVlLCJwYXlwYWwiOnsiZGlzcGxheU5hbWUiOiJwcmFzaGFudHBAa2xvdWRkYXRhLmNvbSIsImNsaWVudElkIjoiQVZjRkFuT1g0RktYN3ViQm93bmo0MlVheDlwOXJLUktxdEhXWnlxWFFKMjc2Z1U3NmN4VmJ3QU1POWROY0twdEJ3TjZaZVlIYklTbXZObXEiLCJwcml2YWN5VXJsIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSIsInVzZXJBZ3JlZW1lbnRVcmwiOiJodHRwczovL2V4YW1wbGUuY29tIiwiYmFzZVVybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9jaGVja291dC5wYXlwYWwuY29tIiwiZGlyZWN0QmFzZVVybCI6bnVsbCwiYWxsb3dIdHRwIjpmYWxzZSwiZW52aXJvbm1lbnROb05ldHdvcmsiOmZhbHNlLCJlbnZpcm9ubWVudCI6ImxpdmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJBUktyWVJEaDNBR1hEelc3c09fM2JTa3EtVTFDN0hHX3VXTkMtejU3TGpZU0ROVU9TYU90SWE5cTZWcFciLCJiaWxsaW5nQWdyZWVtZW50c0VuYWJsZWQiOnRydWUsIm1lcmNoYW50QWNjb3VudElkIjoiVVNEIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sImNvaW5iYXNlRW5hYmxlZCI6ZmFsc2UsIm1lcmNoYW50SWQiOiI3bnJ2anFwNGR0eHprc2M0IiwidmVubW8iOiJvZmYifQ==', 'dropin', {
		  paypal: {
			clientToken: token,
		    container: 'merchant-form',
		    singleUse: false, // Required
		    amount: 10.00, // Required
		    currency: 'USD', // Required
		    locale: 'en_us',
		    enableShippingAddress: true,
		    shippingAddressOverride: {
		      recipientName: 'Scruff McGruff',
		      streetAddress: '1234 Main St.',
		      extendedAddress: 'Unit 1',
		      locality: 'Chicago',
		      countryCodeAlpha2: 'US',
		      postalCode: '60652',
		      region: 'IL',
		      phone: '123.456.7890',
		      editable: false
		    }
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
		    doSomethingWithTheNonce(obj.nonce);
		  }
		});

});
