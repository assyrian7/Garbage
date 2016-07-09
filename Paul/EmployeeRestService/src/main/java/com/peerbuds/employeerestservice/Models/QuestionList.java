package com.peerbuds.employeerestservice.Models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.employeerestservice.Interfaces.JSONConverter;

public class QuestionList implements JSONConverter{

	private JSONObject mainObj;
	private JSONArray inner;
	private List<Question> questions;
	
	public QuestionList(List<Question> questions){
		this.questions = questions;
		mainObj = new JSONObject();
		inner = new JSONArray();
	}

	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		for(Question question: questions){
			inner.put(question.getJSONObject());
		}
		try{
			mainObj.put("questions", inner);
		}catch(JSONException e){
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
