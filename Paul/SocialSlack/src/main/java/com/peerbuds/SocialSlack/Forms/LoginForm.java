package com.peerbuds.SocialSlack.Forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LoginForm {

	@Length(min = 5, max = 15, message = "Username should be between 5 - 15 characters")
	private String username;
	
	@Length(min = 5, max = 15, message = "Password should be between 5 - 15 characters")
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
