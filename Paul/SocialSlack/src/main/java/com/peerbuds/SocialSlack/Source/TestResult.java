package com.peerbuds.SocialSlack.Source;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class TestResult implements Serializable, JSONConverter{
	
	private int userID;
	private int testID;
	private int numberOfCorrect;
	private int numberOfWrong;
	private String timestamp;
	private String key;
	
	private JSONObject inner;
	private JSONObject wrapper;
	private static final long serialVersionUID = 1L;
	
	public TestResult(){
		inner = new JSONObject();
		wrapper = new JSONObject();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	public int getNumberOfCorrect() {
		return numberOfCorrect;
	}

	public void setNumberOfCorrect(int numberOfCorrect) {
		this.numberOfCorrect = numberOfCorrect;
	}

	public int getNumberOfWrong() {
		return numberOfWrong;
	}

	public void setNumberOfWrong(int numberOfWrong) {
		this.numberOfWrong = numberOfWrong;
	}

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		try{
			inner.put("userID", userID);
			inner.put("testID", testID);
			inner.put("numberOfCorrect", numberOfCorrect);
			inner.put("numberOfWrong", numberOfWrong);
			inner.put("timestamp", timestamp);
			wrapper.put("test", inner);
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getHashKey(){
		return "" + userID + testID + numberOfCorrect + numberOfWrong + timestamp;
	}

}
