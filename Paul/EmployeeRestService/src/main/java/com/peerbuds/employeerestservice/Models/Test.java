package com.peerbuds.employeerestservice.Models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.employeerestservice.Interfaces.JSONConverter;


public class Test implements Serializable, JSONConverter{
	
	private JSONObject inner;
	private JSONObject wrapper;
	
	private int userID;
	private int testID;
	private int numberOfQuestions;
	private int points;
	
	private static final long serialVersionUID = 1L;
	
	
	public Test(){
		inner = new JSONObject();
		wrapper = new JSONObject();
	}


	public int getTestID() {
		return testID;
	}


	public void setTestID(int testID) {
		this.testID = testID;
	}

	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		else if(!(object instanceof Test)){
			return false;
		}
		else{
			Test test = (Test)object;
			if(this.testID == test.getTestID() && this.numberOfQuestions == test.getNumberOfQuestions() && this.points == test.getPoints() && this.userID == test.getUserID()){
				
				return true;
			}
		}
		return false;
	}
	public void setJSONObject(){
		try{
			inner.put("testID", testID);
			inner.put("userID", userID);
			inner.put("points", points);
			inner.put("numberOfQuestions", numberOfQuestions);
			wrapper.put("test", inner);
		} catch(JSONException e){
			
		}
	}
	public JSONObject getJSONObject(){
		return wrapper;
	}
	public String toString(){
		return wrapper.toString();
	}
}
