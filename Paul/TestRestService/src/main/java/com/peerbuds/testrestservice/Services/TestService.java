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

import com.peerbuds.testrestservice.DAOs.TestDao;
import com.peerbuds.testrestservice.Implementations.TestDaoImpl;
import com.peerbuds.testrestservice.Models.Test;


@Path("/TestService")
public class TestService {
	
	private static final String SUCCESS_RESULT = "{ \n" + 
												 "  \"result\": \"success\" \n" +
												 "}";
	private static final String FAILURE_RESULT = "{ \n" + 
			 									 "  \"result\": \"failure\" \n" +
			 									 "}";
	private static final String[] operations = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
	private ApplicationContext appContext = new ClassPathXmlApplicationContext("database.properties.xml");
	private TestDao testDao = (TestDaoImpl)appContext.getBean("testDaoImpl");

	
	@GET
	@Path("/tests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTests(){
		return testDao.getAllTests();
	}
	
	@GET
	@Path("/testresults")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTestResults(){
		return testDao.getAllTestResults();
	}
	
	
	@GET
	@Path("tests/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTestByID(@PathParam("testID") int testID){
		String result = testDao.returnTestByID(testID);
		if(result == null){
			return "failure";
		}
		return result;
	}
	
	@GET
	@Path("/testresults/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTestResultsByUser(@PathParam("userID") int userID){
		return testDao.returnTestResultsByUser(userID);
	}
	
	@GET
	@Path("/testresults/{userID}/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTestResultsByUser(@PathParam("userID") int userID, @PathParam("testID") int testID){
		return testDao.returnTestResultsByUserAndTest(userID, testID);
	}
	
	@GET
	@Path("/testresults/{userID}/{testID}/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTestResultsByUserAndTestAndTimeStamp(@PathParam("userID") int userID, @PathParam("testID") int testID, @PathParam("timestamp") String timeStamp){
		return testDao.returnTestResultsByUserAndTestAndTimeStamp(userID, testID, timeStamp);
	}
	
	@POST
	@Path("/tests")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addTest(@FormParam("testID") String testID, @FormParam("numberOfQuestions") String numberOfQuestions, @FormParam("userID") String userID, @Context HttpServletResponse response){
		Test test = new Test();
		test.setTestID(Integer.parseInt(testID));
		test.setNumberOfQuestions(Integer.parseInt(numberOfQuestions));
		test.setUserID(Integer.parseInt(userID));
		test.setPoints(0);
		int result = testDao.addTest(test);
		if(result == 1){
			return "success";
		}
		else{
			return "Test ID taken";
		}
	}
	
	/*
	@POST
	@Path("/tests")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateTest(@FormParam("testID") int testID, @FormParam("numberOfMC") int numberOfMC, @FormParam("numberOfFITB") int numberOfFITB, @FormParam("numberOfTF") int numberOfTF, @Context HttpServletResponse response){
		Test test = new Test();
		test.setTestID(testID);
		test.setNumberOfMC(numberOfMC);
		test.setNumberOfFITB(numberOfFITB);
		test.setNumberOfTF(numberOfTF);
		int result = testDao.updateTest(test);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		else{
			return FAILURE_RESULT;
		}
	}
	*/
	
	@DELETE
	@Path("/tests/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteTest(@PathParam("testID") int testID){
		int result = testDao.deleteTest(testID);
		if(result == 1){
			return "success";
		}
		else{
			return "failure";
		}
	}
	
	
	@OPTIONS
	@Path("/tests")
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
