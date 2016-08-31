/**
 * 
 */
function signUp(){
	
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	
	/*
	var formData = new FormData();
	formData.append("username", username.value);
	formData.append("passoword", password.value);
	formData.append("action", "sign-up");
	*/
		
	
	var request = new XMLHttpRequest();
	//request.open("POST", "http://localhost:8080/PbCorbHub/MainServlet/");
	request.open("POST", "MainServlet", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var result = document.getElementById("res");
	
	request.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			result.innerHTML = "Submitted";
		}
	}
	
	var user = "username=";
	var userValue = user.concat(username.value);
	
	var pass = "&password=";
	var passValue = pass.concat(password.value);
	
	var result = userValue.concat(passValue);
	
	request.send(result);
	
	
	/*
	var params = {
		username: "erererere",
		password: "ryyubububu"
	};
	
	$(document).ready(function(){
		
		$.post("MainServlet", $.params(params), function(response){
			$("#res").text(response);
		});
		
	});
	*/

}