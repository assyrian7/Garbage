package com.peerbuds.employeerestservice.Services;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.peerbuds.employeerestservice.DAOs.QuestionDao;
import com.peerbuds.employeerestservice.Implementations.QuestionDaoImpl;
import com.peerbuds.employeerestservice.Models.QuestionResult;



@Path("/QuestionService")
public class QuestionService {

	
	private static final String SUCCESS_RESULT = "{ \n" + 
			 									 "  \"result\": \"success\" \n" +
			 									 "}";
	private static final String FAILURE_RESULT = "{ \n" + 
												 "  \"result\": \"failure\" \n" +
												 "}";
	private static final String[] operations = {"GET", "POST", "OPTIONS"};
	private ApplicationContext appContext = new ClassPathXmlApplicationContext("database.properties.xml");
	private QuestionDao questionDao = (QuestionDaoImpl)appContext.getBean("questionDaoImpl");
	
	@GET
	@Path("/questions")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuestions(){
		return questionDao.getAllQuestions();
	}
	
	
	@GET
	@Path("/questions/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionsByTest(@PathParam("testID") int testID){
		return questionDao.getQuestionsByTest(testID);
	}
	
	@GET
	@Path("/questions/{testID}/{questionID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionById(@PathParam("testID") int testID, @PathParam("questionID") int questionID){
		String result = questionDao.returnQuestionByID(testID, questionID);
		if(result == null){
			return FAILURE_RESULT;
		}
		return result;
	}
	
	@POST
	@Path("/questionresults")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateQuestionResults(@FormParam("userID") int userID, @FormParam("testID") int testID, @FormParam("questionID") int questionID, @FormParam("answer") String answer){
		QuestionResult questionResult = new QuestionResult();
		questionResult.setUserID(userID);
		questionResult.setTestID(testID);
		questionResult.setQuestionID(questionID);
		questionResult.setAnswer(answer);
		int result = questionDao.updateQuestionResults(questionResult);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		else{
			return FAILURE_RESULT;
		}
	}
	
	@OPTIONS
	@Path("/questions")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOperations(){
		JSONObject mainOperations = new JSONObject();
		try{
			mainOperations.put("operations", operations);
		}catch(JSONException e){
			e.printStackTrace();
		}
		return mainOperations.toString();
	}
}
