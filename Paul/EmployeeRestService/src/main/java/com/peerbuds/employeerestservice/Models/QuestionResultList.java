package com.peerbuds.employeerestservice.Models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.employeerestservice.Interfaces.JSONConverter;

public class QuestionResultList implements JSONConverter{

	private List<QuestionResult> questionResults;
	private JSONObject mainObj;
	private JSONArray objArray;
	
	public QuestionResultList(List<QuestionResult> questionResults){
		setQuestionResults(questionResults);
		mainObj = new JSONObject();
		objArray = new JSONArray();
	}

	public List<QuestionResult> getQuestionResults() {
		return questionResults;
	}

	public void setQuestionResults(List<QuestionResult> questionResults) {
		this.questionResults = questionResults;
	}

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		for(QuestionResult questionResult: questionResults){
			objArray.put(questionResult.getJSONObject());
		}
		try{
			mainObj.put("questions", objArray);
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
