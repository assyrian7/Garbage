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

import com.peerbuds.employeerestservice.DAOs.TestDao;
import com.peerbuds.employeerestservice.Implementations.TestDaoImpl;
import com.peerbuds.employeerestservice.Models.TestResult;

@Path("/TestService")
public class TestService {
	
	private static final String SUCCESS_RESULT = "{ \n" + 
			 									 "  \"result\": \"success\" \n" +
			 									 "}";
	private static final String FAILURE_RESULT = "{ \n" + 
				 							     "  \"result\": \"failure\" \n" +
				 							     "}";
	private static final String[] operations = {"GET", "POST", "OPTIONS"};
	private ApplicationContext appContext = new ClassPathXmlApplicationContext("database.properties.xml");
	private TestDao testDao = (TestDaoImpl)appContext.getBean("testDaoImpl");
	
	@GET
	@Path("/tests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTests(){
		return testDao.getAllTests();
	}
	
	@GET
	@Path("/tests/{testID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String returnTestByID(@PathParam("testID") int testID){
		String result = testDao.returnTestByID(testID);
		if(result == null){
			return FAILURE_RESULT;
		}
		return result;
	}
	
	@POST
	@Path("/testresults")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateTestResults(@FormParam("userID") int userID, @FormParam("testID") int testID){
		TestResult testResult = new TestResult();
		testResult.setUserID(userID);
		testResult.setTestID(testID);
		int result = testDao.updateTestResults(testResult);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		else{
			return FAILURE_RESULT;
		}
	}
	
	@OPTIONS
	@Path("/tests")
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
