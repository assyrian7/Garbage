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
public class TestService {

	public Test getTestFromAPI(int testID){
		String testData = getURL("http://localhost:8080/TestRestService/rest/TestService/tests/" + testID);
		return getTestFromData(testData);
	}
	
	public List<Test> getUserTestsFromAPI(int userID){
		String testData = getURL("http://localhost:8080/TestRestService/rest/TestService/user-tests/" + userID);
		return getTestsFromData(testData);
	}
	
	public Test getTestFromData(String testData){
				
		Test test = null;
		JSONObject wrapper = null;
		JSONObject inner = null;
		try{
			wrapper = new JSONObject(testData);
			inner = wrapper.getJSONObject("test");
			int testID = inner.getInt("testID");
			int userID = inner.getInt("userID");
			int numberOfQuestions = inner.getInt("numberOfQuestions");
			int points = inner.getInt("points");
			test = new Test();
			test.setTestID(testID);
			test.setUserID(userID);
			test.setNumberOfQuestions(numberOfQuestions);
			test.setPoints(points);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return test;
	}
	
	public List<Test> getTestsFromData(String testData){
		List<Test> tests = new ArrayList<Test>();
		Test test = null;
		JSONObject mainObj = null;
		JSONArray mainArray = null;
		JSONObject dataTest = null;
		try{
			mainObj = new JSONObject(testData);
			mainArray = mainObj.getJSONArray("tests");
			for(int i = 0; i < mainArray.length(); i++){
				dataTest = mainArray.getJSONObject(i).getJSONObject("test");
				test = new Test();
				test.setTestID(dataTest.getInt("testID"));
				test.setUserID(dataTest.getInt("userID"));
				test.setNumberOfQuestions(dataTest.getInt("numberOfQuestions"));
				test.setPoints(dataTest.getInt("points"));
				tests.add(test);
			}
			
		} catch(JSONException e){
			e.printStackTrace();
		}
		return tests;
	}
	
	public String addTestToAPI(Test test){
		String url = "http://localhost:8080/TestRestService/rest/TestService/tests";
		HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("userID", Integer.toString(test.getUserID())));
    	urlParameters.add(new BasicNameValuePair("testID", Integer.toString(test.getTestID())));
    	urlParameters.add(new BasicNameValuePair("numberOfQuestions", Integer.toString(test.getNumberOfQuestions())));
    	urlParameters.add(new BasicNameValuePair("name", test.getName()));
    	urlParameters.add(new BasicNameValuePair("descirption", test.getDescription()));
    	urlParameters.add(new BasicNameValuePair("categories", test.getCategories()));
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
	
	public String addTestResultToAPI(TestResult testResult){
		String url = "http://localhost:8080/EmployeeRestService/rest/TestService/testresults";
		HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("userID", Integer.toString(testResult.getUserID())));
    	urlParameters.add(new BasicNameValuePair("testID", Integer.toString(testResult.getTestID())));
    	urlParameters.add(new BasicNameValuePair("timestamp", testResult.getTimestamp()));
    	urlParameters.add(new BasicNameValuePair("key", testResult.getKey()));

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
