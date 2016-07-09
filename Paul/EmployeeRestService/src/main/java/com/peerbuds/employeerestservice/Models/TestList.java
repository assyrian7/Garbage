package com.peerbuds.employeerestservice.Models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.employeerestservice.Interfaces.JSONConverter;

public class TestList implements JSONConverter{

	private List<Test> tests;
	private JSONObject mainObj;
	private JSONArray objArray;
	
	public TestList(List<Test> tests){
		this.tests = tests;
		mainObj = new JSONObject();
		objArray = new JSONArray();
	}

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		try{
			for(Test test: tests){
				objArray.put(test.getJSONObject());
			}
			mainObj.put("tests", objArray);
		} catch(JSONException e){
			e.printStackTrace();
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
