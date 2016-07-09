package com.peerbuds.profileservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Path("/ProfileService")
public class ProfileService {

	private ApplicationContext appContext = new ClassPathXmlApplicationContext("database.properties.xml");
	private ProfileDao profileDao = (ProfileDaoImpl)appContext.getBean("profileDaoImpl");
	
	public ProfileService(){
		
	}
	
	@GET
	@Path("/profiles")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProfiles(){
		return profileDao.returnAllProfiles();
	}
	
	@GET
	@Path("/profiles/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProfileByUsername(@PathParam("username") String username){
		Profile profile = profileDao.getProfileByUsername(username);
		if(profile == null){
			return "profile not found";
		}
		profile.setJSONObject();
		return profile.toString();
	}
	
	@POST
	@Path("/profiles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateProfile(@FormParam("id") int id, @FormParam("firstname") String firstname, @FormParam("lastname") String lastname, @FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email){
		
		Profile profile = new Profile();
		profile.setId(id);
		profile.setUsername(username);
		profile.setPassword(password);
		profile.setFirstname(firstname);
		profile.setLastname(lastname);
		profile.setEmail(email);
		
		int result = profileDao.update(profile);
		if(result == 0){
			return "Username already used";
		} else{
			return "Success";
		}
	}
	@POST
	@Path("/profiles/signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String signup(@FormParam("id") String id, @FormParam("firstname") String firstname, @FormParam("lastname") String lastname, @FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email){
		
		Profile profile = new Profile();
		profile.setUsername(username);
		profile.setPassword(password);
		profile.setFirstname(firstname);
		profile.setLastname(lastname);
		profile.setEmail(email);
		profile.setId(Integer.parseInt(id));
		int result = profileDao.signUp(profile);
		
		if(result == 1){
			return "Username already used";
		} 
		else if(result == 2){
			return "Email already used";
		} else {
			return "Success";
		}
		
	}
	@POST
	@Path("/profiles/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String login(@FormParam("username") String username, @FormParam("password") String password){
		
		Profile profile = new Profile();
		profile.setPassword(password);
		profile.setUsername(username);
		int result = profileDao.login(profile);
		
		if(result == 0){
			return "No profile found for that username.";
		} else if(result == 1){
			return "Password incorrect for that username.";
		} else{
			return "Success";
		}
	}
	/*
	@PUT
	@Path("/profiles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addProfile(@FormParam("firstname") String firstname, @FormParam("lastname") String lastname, @FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email){
		
		Profile profile = new Profile();
		profile.setUsername(username);
		profile.setPassword(password);
		profile.setFirstname(firstname);
		profile.setLastname(lastname);
		profile.setEmail(email);
		
		int result = profileDao.add(profile);
		if(result == 0){
			return result("Username already used");
		} else{
			return result("Success");
		}
	}
	*/
	@DELETE
	@Path("/profiles/{profileID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteProfile(@PathParam("profileID") String id, String result){
		
		Profile profile = profileDao.getProfileById(Integer.parseInt(id));
		profileDao.delete(profile);
		return "deleted";
		
	}

}


