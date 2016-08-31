package com.peerbuds.SocialSlack.Source;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class Question implements Serializable, JSONConverter, Comparable<Question>{
	
	private JSONObject inner;
	private JSONObject wrapper;
	
	private int testID;
	private int questionID;
	private String question;
	private String questionType;
	private String possibleAnswers;
	private String correctAnswers;
	private String key;
	
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
	public int compareTo(Question arg0) {
		if(this.questionID < arg0.getQuestionID()){
			return -1;
		}
		else if(this.questionID > arg0.getQuestionID()){
			return 1;
		}
		return 0;
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getHashKey(){
		return "" + testID + questionID + question + questionType + possibleAnswers + correctAnswers;
	}
	
	
	
}
