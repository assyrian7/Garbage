package com.peerbuds.testrestservice.Models;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.testrestservice.Interface.JSONConverter;

public class Question implements Serializable, JSONConverter{
	
	private JSONObject inner;
	private JSONObject wrapper;
	private int testID;
	private int questionID;
	private String question;
	private String questionType;
	private String possibleAnswers;
	private String correctAnswers;
	private static final long serialVersionUID = 1L;
	
	public Question(){
		inner = new JSONObject();
		wrapper = new JSONObject();
	}
	public int getTestID() {
		return testID;
	}
	public void setTestID(int testID) {
		this.testID = testID;
	}
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getPossibleAnswers() {
		return possibleAnswers;
	}
	public void setPossibleAnswers(String possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}
	public String getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(String correctAnswer) {
		this.correctAnswers = correctAnswer;
	}
	public void setJSONObject(){
		try {
			inner.put("testID", testID);
			inner.put("questionID", questionID);
			inner.put("questionType", questionType);
			inner.put("actualQuestion", question);
			inner.put("possibleAnswers", possibleAnswers);
			inner.put("correctAnswer", correctAnswers);
			wrapper.put("question", inner);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public JSONObject getJSONObject(){
		return wrapper;
	}
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		else if(!(object instanceof Question)){
			return false;
		}
		else{
			Question question = (Question)object;
			if(this.testID == question.getTestID() && this.questionID == question.getQuestionID() && this.questionType == question.getQuestionType() && this.possibleAnswers == question.getPossibleAnswers() && this.correctAnswers == question.getCorrectAnswers()){
				return true;
			}
		}
		return false;
	}
	public String toString(){
		return wrapper.toString();
	}
	
	
}
