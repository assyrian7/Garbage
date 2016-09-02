package com.peerbuds.SocialSlack.Source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	private QuestionService questionService = new QuestionService();
	private TestService testService = new TestService();
	private Logger logger = Logger.getLogger(AjaxController.class);
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView ajax(ModelMap model){
		
		return new ModelAndView("ajax", "message", "Ajax test");
		
	}
	
	@RequestMapping(value="/randInt", method = RequestMethod.GET)
	public @ResponseBody String getTime(){
		Random rand = new Random();
		float r = rand.nextFloat() * 100;
        String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
        System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
        return result;
	}
	
	
	@RequestMapping(value="/submit-created-test", method = RequestMethod.POST)
	public @ResponseBody Callable<String> submitCreatedTest(HttpServletRequest request){
		
		
		return new Callable<String>(){
			
			public String call(){
				HttpSession session = request.getSession();
				if(session.getAttribute("profile") != null){
										
					Profile profile = (Profile)session.getAttribute("profile");
					
					String testData = request.getParameter("testData");
					int testSize = Integer.parseInt(request.getParameter("testSize"));
					  
					Test test = new Test();
					test.setNumberOfQuestions(testSize);
					test.setUserID(profile.getId());
					test.setPoints(0);
					test.setTestID(test.hashCode());
					
					List<Question> questions = questionService.getQuestionsFromSubmission(testData);
					for(int i = 0; i < testSize; i++){
						questions.get(i).setQuestionID(i + 1);
					}
					for(Question question: questions){
						question.setTestID(test.getTestID());
						question.setKey(Encrypt.hash(question.getHashKey().toCharArray(), Salt.salt.getBytes(), question.getHashKey().length(), 256));
					}
					String questionResult = "";
					for(Question question: questions){
						questionResult = questionService.addQuestionToAPI(question);
					}
					String testResult = "";
					testResult = testService.addTestToAPI(test);
					return questionResult;
				} else{
					return "Could not complete request";
				}
				
			}
			
		};
		
	}
	
	@RequestMapping(value = "/submit-taken-test", method = RequestMethod.POST)
	public @ResponseBody Callable<String> submitTakenTest(HttpServletRequest request){
		return new Callable<String>(){
			public String call(){
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy|HH-mm-ss");
				String timestamp = dateFormat.format(new Date()).toString();
				
				HttpSession session = request.getSession();
				if(session.getAttribute("profile") != null){
					
					Profile profile = (Profile)session.getAttribute("profile");
					String testData = request.getParameter("testData");
					int testID = Integer.parseInt(request.getParameter("testID"));
					
					List<QuestionResult> questionResults = questionService.getQuestionResultsFromSubmission(testData);
					for(QuestionResult questionResult: questionResults){
						questionResult.setUserID(profile.getId());
						questionResult.setTimestamp(timestamp);
						questionResult.setKey(questionResult.getHashKey());
					}
					String questionSubmissionResult = "";
					for(QuestionResult questionResult: questionResults){
						questionSubmissionResult = questionService.addQuestionResultToAPI(questionResult);
					}
					
					
					TestResult testResult = new TestResult();
					testResult.setUserID(profile.getId());
					testResult.setTestID(testID);
					testResult.setTimestamp(timestamp);
					testResult.setKey(testResult.getHashKey());
					
					String testSubmissionResult = "";
					
					testSubmissionResult = testService.addTestResultToAPI(testResult);
					
					return testSubmissionResult;
					
				}
				else{
					return "Could not complete request";
				}
				
			}
		};
	}
	
	@RequestMapping(value="search-categories", method = RequestMethod.GET)
	public @ResponseBody String searchCategories(HttpServletRequest request){
		
		int index = 0;
		String[] results = new String[Categories.categories.length];
		String category = request.getParameter("category");
		String[] categories = Categories.categories;
		for(int i = 0; i < categories.length; i++){
			if(category.length() == 1){
				if(categories[i].charAt(0) == category.charAt(0)){
					results[index] = categories[i];
					index++;
				}
			}
			else if(categories[i].substring(0, category.length()).equals(category)){
				results[index] = categories[i];
				index++;
			}
			else if(category.length() >= 3 && categories[i].contains(category)){
				results[index] = categories[i];
			}
		}
		return String.join(";", categories);
	}
	
	private String getResult(String data){
		JSONObject mainObj = null;
		String result = null;
		try{
			mainObj = new JSONObject(data);
			result = mainObj.getString("result");
		} catch(JSONException e){
			e.printStackTrace();
		}
		return result;
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
