package com.peerbuds.SocialSlack.Forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class SignUpForm {

	@Length(min = 1, max = 15, message = "First name should be between 5 - 15 characters")
	private String firstname;
	
	@Length(min = 1, max = 15, message = "Last name should be between 5 - 15 characters")
	private String lastname;
	
	@Length(min = 5, max = 15, message = "Username should be between 5 - 15 characters")
	private String username;
	
	@Length(min = 5, max = 15, message = "Password should be between 5 - 15 characters")
	private String password;
	
	@Pattern(regexp = ".+@.+\\..+", message = "Invalid email")
	private String email;
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
