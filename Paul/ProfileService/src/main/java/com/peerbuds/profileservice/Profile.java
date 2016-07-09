package com.peerbuds.profileservice;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class Profile implements Serializable, JSONConverter{

	
	private int id;
	
	private JSONObject wrapper;
	private JSONObject inner;
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	
	private static final long serialVersionUID = 1L;
	
	public Profile(){
		wrapper = new JSONObject();
		inner = new JSONObject();
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

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		try{
			inner.put("id", id);
			inner.put("firstname", firstname);
			inner.put("lastname", lastname);
			inner.put("username", username);
			inner.put("password", password);
			inner.put("email", email);
			wrapper.put("profile", inner);
		} catch(JSONException e){
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getJSONObject() {
		// TODO Auto-generated method stub
		return wrapper;
	}
	
	public String toString(){
		return wrapper.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
