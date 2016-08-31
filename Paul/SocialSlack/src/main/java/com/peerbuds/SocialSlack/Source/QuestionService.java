package com.peerbuds.SocialSlack.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
	
	public List<Question> getQuestionsByTest(int testID){
		String questionData = getURL("http://localhost:8080/TestRestService/rest/QuestionService/questions/" + testID);
		return getQuestionsFromAPI(questionData);
	}
	
	public List<Question> getQuestionsFromAPI(String data){
		List<Question> questions = new ArrayList<Question>();
		Question question = null;
		JSONObject mainObj = null;
		JSONArray mainArray = null;
		JSONObject dataQuestion = null;
		try{
			mainObj = new JSONObject(data);
			mainArray = mainObj.getJSONArray("questions");
			for(int i = 0; i < mainArray.length(); i++){
				dataQuestion = mainArray.getJSONObject(i).getJSONObject("question");
				question = new Question();
				question.setQuestionID(dataQuestion.getInt("questionID"));
				question.setTestID(dataQuestion.getInt("testID"));
				question.setQuestion(dataQuestion.getString("actualQuestion"));
				question.setQuestionType(dataQuestion.getString("questionType"));
				question.setPossibleAnswers(dataQuestion.getString("possibleAnswers"));
				question.setCorrectAnswers(dataQuestion.getString("correctAnswer"));
				questions.add(question);
			}
			
		} catch(JSONException e){
			e.printStackTrace();
		}
		return questions;
	}
	
	public List<Question> getQuestionsFromSubmission(String data){
		List<Question> questions = new ArrayList<Question>();
		Question question = null;
		JSONObject mainObj = null;
		JSONArray mainArray = null;
		JSONObject dataQuestion = null;
		try{
			mainObj = new JSONObject(data);
			mainArray = mainObj.getJSONArray("questions");
			for(int i = 0; i < mainArray.length(); i++){
				dataQuestion = mainArray.getJSONObject(i).getJSONObject("question");
				question = new Question();
				question.setQuestionID(dataQuestion.getInt("questionID"));
				question.setQuestion(dataQuestion.getString("actualQuestion"));
				question.setQuestionType(dataQuestion.getString("questionType"));
				question.setPossibleAnswers(dataQuestion.getString("possibleAnswers"));
				question.setCorrectAnswers(dataQuestion.getString("correctAnswers"));
				questions.add(question);
			}
			
		} catch(JSONException e){
			e.printStackTrace();
		}
		return questions;
	}
	
	public List<QuestionResult> getQuestionResultsFromAPI(String data){
		List<QuestionResult> questionResults = new ArrayList<QuestionResult>();
		QuestionResult questionResult = null;
		JSONObject mainObj = null;
		JSONArray mainArray = null;
		JSONObject dataQuestionResult = null;
		try{
			mainObj = new JSONObject(data);
			mainArray = mainObj.getJSONArray("questions");
			for(int i = 0; i < mainArray.length(); i++){
				dataQuestionResult = mainArray.getJSONObject(i).getJSONObject("question");
				questionResult = new QuestionResult();
				questionResult.setQuestionID(dataQuestionResult.getInt("questionID"));
				questionResult.setUserID(dataQuestionResult.getInt("userID"));
				questionResult.setTestID(dataQuestionResult.getInt("testID"));
				questionResult.setAnswer(dataQuestionResult.getString("answer"));
				questionResult.setTimestamp(dataQuestionResult.getString("timestamp"));
				questionResults.add(questionResult);
			}
			
		} catch(JSONException e){
			e.printStackTrace();
		}
		return questionResults;
	}
	
	public List<QuestionResult> getQuestionResultsFromSubmission(String data){
		List<QuestionResult> questionResults = new ArrayList<QuestionResult>();
		QuestionResult questionResult = null;
		JSONObject mainObj = null;
		JSONArray mainArray = null;
		JSONObject dataQuestionResult = null;
		try{
			mainObj = new JSONObject(data);
			mainArray = mainObj.getJSONArray("questions");
			for(int i = 0; i < mainArray.length(); i++){
				dataQuestionResult = mainArray.getJSONObject(i).getJSONObject("question");
				questionResult = new QuestionResult();
				questionResult.setQuestionID(dataQuestionResult.getInt("questionID"));
				questionResult.setTestID(dataQuestionResult.getInt("testID"));
				questionResult.setAnswer(dataQuestionResult.getString("answer"));
				questionResults.add(questionResult);
			}
			
		} catch(JSONException e){
			e.printStackTrace();
		}
		return questionResults;
	}
	
	public String addQuestionToAPI(Question question){
		
		String url = "http://localhost:8080/TestRestService/rest/QuestionService/questions";
		HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("questionID", Integer.toString(question.getQuestionID())));
    	urlParameters.add(new BasicNameValuePair("testID", Integer.toString(question.getTestID())));
    	urlParameters.add(new BasicNameValuePair("question", question.getQuestion()));
    	urlParameters.add(new BasicNameValuePair("questionType", question.getQuestionType()));  	
    	urlParameters.add(new BasicNameValuePair("possibleAnswers", question.getPossibleAnswers()));
    	urlParameters.add(new BasicNameValuePair("correctAnswer", question.getCorrectAnswers()));
    	urlParameters.add(new BasicNameValuePair("key", question.getKey()));
    	try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			response = client.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result.toString();
		
	}
	public String addQuestionResultToAPI(QuestionResult questionResult){
		
		String url = "http://localhost:8080/EmployeeRestService/rest/QuestionService/questionresults";
		HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("userID", Integer.toString(questionResult.getUserID())));
    	urlParameters.add(new BasicNameValuePair("questionID", Integer.toString(questionResult.getQuestionID())));
    	urlParameters.add(new BasicNameValuePair("testID", Integer.toString(questionResult.getTestID())));
    	urlParameters.add(new BasicNameValuePair("answer", questionResult.getAnswer()));
    	urlParameters.add(new BasicNameValuePair("timestamp", questionResult.getTimestamp()));
    	urlParameters.add(new BasicNameValuePair("key", questionResult.getKey()));
    	try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			response = client.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result.toString();
		
	}
	
	private String getURL(String url) {
		StringBuilder response = null;
		try{
	        URL website = new URL(url);
	        URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));
	
	        response = new StringBuilder();
	        String inpostLine;
	
	        while ((inpostLine = in.readLine()) != null) 
	            response.append(inpostLine);
	
	        in.close();
		} catch(Exception e){
			
		}
		return response.toString();
    }
	
}
