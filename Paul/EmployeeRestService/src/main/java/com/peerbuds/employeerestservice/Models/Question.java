package com.peerbuds.employeerestservice.Models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.employeerestservice.ETC.DIV;
import com.peerbuds.employeerestservice.ETC.Randomizer;
import com.peerbuds.employeerestservice.Interfaces.JSONConverter;


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


	public void setCorrectAnswers(String correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	
	
	public Question(){
		inner = new JSONObject();
		wrapper = new JSONObject();
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
			if(this.getTestID() == question.getTestID() && this.getQuestionID() == question.getQuestionID() && this.getQuestion() == question.getQuestion() && this.getPossibleAnswers() == question.getPossibleAnswers()){
				return true;
			}
		}
		return false;
	}
	@Override
	public void setJSONObject() {
		// TODO Auto-generated method stub
		if(questionType.equalsIgnoreCase("FITB")){
			String[] corrects = correctAnswers.split(DIV.DELIMITER);
			for(int i = 0; i < corrects.length; i++){
				possibleAnswers = possibleAnswers.replace(corrects[i], DIV.BLANK);
			}
			
		}
		else if(questionType.equalsIgnoreCase("MC")){
			this.possibleAnswers = String.join(DIV.DELIMITER, Randomizer.shuffle(possibleAnswers.split(DIV.DELIMITER)));
		}
		try{
			inner.put("testID", testID);
			inner.put("questionID", questionID);
			inner.put("question", question);
			inner.put("questionType", questionType);
			inner.put("possibleAnswers", String.join(DIV.DELIMITER, possibleAnswers));
			wrapper.put("question", inner);
		}catch(JSONException e){
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
}
