package com.peerbuds.testrestservice.Models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.testrestservice.Interface.JSONConverter;

public class QuestionResult implements Serializable, JSONConverter{
	
	private int userID;
	private int questionID;
	private int testID;
	private String answer;
	private String timestamp;
	private int numberOfAttempts;
	
	private static final long serialVersionUID = 1L;
	private JSONObject inner;
	private JSONObject wrapper;
	
	public QuestionResult(){
		inner = new JSONObject();
		wrapper = new JSONObject();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getNumberOfAttempts() {
		return numberOfAttempts;
	}

	public void setNumberOfAttempts(int numberOfAttempts) {
		this.numberOfAttempts = numberOfAttempts;
	}
	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		try{
			inner.put("userID", userID);
			inner.put("questionID", questionID);
			inner.put("testID", testID);
			inner.put("answer", answer);
			inner.put("numberOfAttempts", numberOfAttempts);
			inner.put("timestamp", timestamp);
			wrapper.put("question", inner);
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	

	
}
