package com.peerbuds.testrestservice.Models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.testrestservice.Interface.JSONConverter;

public class QuestionList implements JSONConverter{

	private List<Question> questions;
	private JSONObject mainObj;
	private JSONArray objectArray;
	
	public QuestionList(List<Question> questions){
		this.questions = questions;
		mainObj = new JSONObject();
		objectArray = new JSONArray();
		
	}

	public void setJSONObject(){
		for(Question question: questions){
			objectArray.put(question.getJSONObject());
		}
		try {
			mainObj.put("questions", objectArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONObject getJSONObject(){
		return mainObj;
	}
	public String toString(){
		return mainObj.toString();
	}
	
}
