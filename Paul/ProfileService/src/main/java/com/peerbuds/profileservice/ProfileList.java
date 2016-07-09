package com.peerbuds.profileservice;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileList implements JSONConverter{

	private List<Profile> profiles;
	private JSONObject mainObj;
	private JSONArray objectArray;
	
	public ProfileList(List<Profile> profiles){
		this.profiles = profiles;
		mainObj = new JSONObject();
		objectArray = new JSONArray();
		
	}

	public void setJSONObject(){
		for(Profile profile: profiles){
			objectArray.put(profile.getJSONObject());
		}
		try {
			mainObj.put("profiles", objectArray);
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

