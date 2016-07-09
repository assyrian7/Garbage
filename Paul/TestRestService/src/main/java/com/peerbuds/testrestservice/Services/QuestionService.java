package com.peerbuds.testrestservice.Services;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.peerbuds.testrestservice.DAOs.QuestionDao;
import com.peerbuds.testrestservice.Implementations.QuestionDaoImpl;
import com.peerbuds.testrestservice.Models.Question;

@Service
@Path("/QuestionService")
public class QuestionService {
	
	private static final String SUCCESS_RESULT = "{ \n" + 
												 "  \"result\": \"success\" \n" +
												 "}";
	private static final String FAILURE_RESULT = "{ \n" + 
			 									 "  \"result\": \"failure\" \n" +
			 									 "}";
	private static final String NOTHING_RESULT = "{ \n" + 
			 									 "  \"result\": \"Nothing to delete\" \n" +
			 									 "}";
	private static final String[] operations = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
	
	private ApplicationContext appContext = new ClassPathXmlApplicationContext("database.properties.xml");
	private QuestionDao questionDao = (QuestionDaoImpl)appContext.getBean("questionDaoImpl");

	@GET
	@Path("/questions")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuestions(){
		return questionDao.getAllQuestions();
	}
	
	@GET
	@Path("/questionresults")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllQuestionResults(){
		return questionDao.getAllQuestionResults();
	}
	
	@GET
	@Path("/validate/{userID}/{testID}/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getValidation(@PathParam("userID") int userID, @PathParam("testID") int testID, @PathParam("timestamp") String timeStamp){
		return questionDao.getValidation(userID, testID, timeStamp);
	}
	
	@GET
	@Path("questions/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionsByTest(@PathParam("testID") int testID){
		return questionDao.returnQuestionsByTest(testID);
	}
	
	@GET
	@Path("questionresults/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionResultsByUser(@PathParam("userID") int userID){
		return questionDao.returnQuestionResultsByUser(userID);
	}
	
	@GET
	@Path("questionresults/{userID}/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionResultsByUserAndTest(@PathParam("userID") int userID, @PathParam("testID") int testID){
		return questionDao.returnQuestionResultsByUserAndTest(userID, testID);
	}
	
	@GET
	@Path("questionresults/{userID}/{testID}/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionResultsByUserAndTestAndTimeStamp(@PathParam("userID") int userID, @PathParam("testID") int testID, @PathParam("timestamp") String timestamp){
		return questionDao.returnQuestionResultsByUserAndTestAndTimeStamp(userID, testID, timestamp);
	}
	
	@GET
	@Path("/questions/{testID}/{questionID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuestionByID(@PathParam("testID") int testID, @PathParam("questionID") int questionID){
		String result = questionDao.returnQuestionByID(testID, questionID);
		if(result == null){
			return FAILURE_RESULT;
		}
		return result;
	}
	
	
	@POST
	@Path("/questions")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addQuestion(@FormParam("questionID") String questionID, @FormParam("testID") String testID, @FormParam("question") String question, @FormParam("questionType") String questionType, @FormParam("possibleAnswers") String possibleAnswers, @FormParam("correctAnswer") String correctAnswer, @Context HttpServletResponse response){
		Question addQuestion = new Question();
		addQuestion.setQuestionID(Integer.parseInt(questionID));
		addQuestion.setTestID(Integer.parseInt(testID));
		addQuestion.setQuestion(question);
		addQuestion.setQuestionType(questionType);
		addQuestion.setPossibleAnswers(possibleAnswers);
		addQuestion.setCorrectAnswers(correctAnswer);
		int result = questionDao.addQuestion(addQuestion);
		if(result == 1){
			return "Success";
		}
		else{
			return "Question with same ids exists";
		}
	}
	
	/*
	@POST
	@Path("/questions")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateQuestion(@FormParam("testID") int testID, @FormParam("questionID") int questionID, @FormParam("question") String question, @FormParam("questionType") String questionType, @FormParam("possibleAnswers") String possibleAnswers, @FormParam("correctAnswer") String correctAnswer, @Context HttpServletResponse reponse){
		Question updateQuestion = new Question();
		updateQuestion.setTestID(testID);
		updateQuestion.setQuestionID(questionID);
		updateQuestion.setQuestion(question);
		updateQuestion.setQuestionType(questionType);
		updateQuestion.setPossibleAnswers(possibleAnswers);
		updateQuestion.setCorrectAnswers(correctAnswer);
		int result = questionDao.updateQuestion(updateQuestion);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		else{
			return FAILURE_RESULT;
		}
	}
	*/
	
	
	@DELETE
	@Path("/questions/{testID}/{questionID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuestion(@PathParam("testID") int testID, @PathParam("questionID") int questionID){
		int result = questionDao.deleteQuestion(testID, questionID);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		else{
			return NOTHING_RESULT;
		}
	}
	
	@DELETE
	@Path("/questions/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteTestQuestions(@PathParam("testID") int testID){
		int result = questionDao.deleteTestQuestions(testID);
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
		try {
			mainOperations.put("operations", operations);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mainOperations.toString();
	}
}
