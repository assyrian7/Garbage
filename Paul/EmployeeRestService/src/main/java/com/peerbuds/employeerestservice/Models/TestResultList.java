package com.peerbuds.employeerestservice.Models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.employeerestservice.Interfaces.JSONConverter;

public class TestResultList implements JSONConverter{
	
	private JSONObject mainObj;
	private JSONArray objArray;
	private List<TestResult> testResults;
	
	public TestResultList(List<TestResult> testResults){
		mainObj = new JSONObject();
		objArray = new JSONArray();
		setTestResults(testResults);
	}

	public List<TestResult> getTestResults() {
		return testResults;
	}

	public void setTestResults(List<TestResult> testResults) {
		this.testResults = testResults;
	}

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		for(TestResult testResult: testResults){
			objArray.put(testResult.getJSONObject());
		}
		try{
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

