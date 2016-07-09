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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	public String addTestToAPI(Test test){
		String url = "http://localhost:8080/TestRestService/rest/TestService/tests";
		HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("userID", Integer.toString(test.getUserID())));
    	urlParameters.add(new BasicNameValuePair("testID", Integer.toString(test.getTestID())));
    	urlParameters.add(new BasicNameValuePair("numberOfQuestions", Integer.toString(test.getNumberOfQuestions())));
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
