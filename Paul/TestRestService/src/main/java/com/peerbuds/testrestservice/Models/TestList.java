package com.peerbuds.testrestservice.Models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.testrestservice.Interface.JSONConverter;

public class TestList implements JSONConverter{
	
	private List<Test> tests;
	private JSONObject mainObj;
	private JSONArray objectArray;
	
	public TestList(List<Test> tests){
		this.tests = tests;
		mainObj = new JSONObject();
		objectArray = new JSONArray();
	}

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		for(Test test: tests){
			objectArray.put(test.getJSONObject());
		}
		try{
			mainObj.put("tests", objectArray);
		} catch(JSONException e){
			
		}
		
	}

	@Override
	public JSONObject getJSONObject() {
		// TODO Auto-generated method stub
		return mainObj;
	}
	public String toString(){
		return mainObj.toString();
	}
	
	
	
}
