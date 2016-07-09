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
public class ProfileService {
	
	public ProfileService(){
		
	}
	
	public String getData(String url) {
		StringBuilder response = null;
		try{
	        URL website = new URL(url);
	        URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));
	
	        response = new StringBuilder();
	        String inputLine;
	
	        while ((inputLine = in.readLine()) != null) 
	            response.append(inputLine);
	
	        in.close();
		} catch(Exception e){
			
		}
		return response.toString();
    }
    
	public Profile getProfileByUsername(String username){
		
		Profile profile = new Profile();
		String data = getData("http://localhost:8080/ProfileService/rest/ProfileService/profiles/" + username);
		
		if(data.equals("profile not found")){
			return null;
		}
		
		JSONObject mainObj = null;
		JSONObject profileObj = null;
		try{
			mainObj = new JSONObject(data);
			profileObj = mainObj.getJSONObject("profile");
			profile.setFirstname(profileObj.getString("firstname"));
			profile.setLastname(profileObj.getString("lastname"));
			profile.setId(profileObj.getInt("id"));
			profile.setEmail(profileObj.getString("email"));
			profile.setUsername(profileObj.getString("username"));
			profile.setPassword(profileObj.getString("password"));
		} catch(JSONException e){
			e.printStackTrace();
		}
		
		return profile;
	}
	
    public String signup(String url, Profile profile){
    	
    	HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("id", Integer.toString(profile.getId())));
    	urlParameters.add(new BasicNameValuePair("firstname", profile.getFirstname()));
    	urlParameters.add(new BasicNameValuePair("lastname", profile.getLastname()));
    	urlParameters.add(new BasicNameValuePair("username", profile.getUsername()));
    	urlParameters.add(new BasicNameValuePair("password", profile.getPassword()));
    	urlParameters.add(new BasicNameValuePair("email", profile.getEmail()));  	
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
    
    public String updateProfile(String url, Profile profile){
    	
    	HttpResponse response = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	
    	urlParameters.add(new BasicNameValuePair("id", profile.getId() + ""));
    	urlParameters.add(new BasicNameValuePair("firstname", profile.getFirstname()));
    	urlParameters.add(new BasicNameValuePair("lastname", profile.getLastname()));
    	urlParameters.add(new BasicNameValuePair("email", profile.getEmail()));  	
    	urlParameters.add(new BasicNameValuePair("username", profile.getUsername()));
    	urlParameters.add(new BasicNameValuePair("password", profile.getPassword()));
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
