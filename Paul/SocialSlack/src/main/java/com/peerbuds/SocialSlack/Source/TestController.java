package com.peerbuds.SocialSlack.Source;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {

	private TestService testService = new TestService();
	private QuestionService questionService = new QuestionService();
	
	@RequestMapping(value = "/{testID}", method = RequestMethod.GET)
	public Callable<String> accessTest(@PathVariable("testID") String id, HttpServletRequest request){
			
		return new Callable<String>(){
			public String call(){
				
				String action = request.getParameter("action");
				
				if(action == null){
					return "redirect:?action=view_test";
				}
				
				else if(action.equals("view_test")){
					int testID = Integer.parseInt(id);
					Test test = testService.getTestFromAPI(testID);
					if(test != null){
						List<Question> questions = questionService.getQuestionsByTest(testID);
						Collections.sort(questions);
						request.setAttribute("questions", (List<Question>)questions);
						request.setAttribute("test", (Test)test);
								
						return "view-test";
					}
					else{
						request.setAttribute("result", "The test you are requesting does not exist");
						return "result";
					}
				}
				else if(action.equals("edit_test")){
					int testID = Integer.parseInt(id);
					Test test = testService.getTestFromAPI(testID);			
					if(test != null){
						
						return "edit-test";
					}
					else{
						request.setAttribute("result", "The test you are requesting does not exist");
						return "result";
					}
				}
				else if(action.equals("take_test")){
					int testID = Integer.parseInt(id);
					Test test = testService.getTestFromAPI(testID);
					HttpSession session = request.getSession();
					Profile profile = (Profile)session.getAttribute("profile");
					if(profile == null){
						return "redirect:http://localhost:8080/SocialSlack/user/login";
					}
					else if(test != null){
						List<Question> questions = questionService.getQuestionsByTest(testID);
						Collections.sort(questions);
						request.setAttribute("questions", (List<Question>)questions);
						return "take-test";
					}
					else{
						request.setAttribute("result", "The test you are requesting does not exist");
						return "result";
					}
				}
				else{
					request.setAttribute("action", "view_test");
					return "redirect:";
				}
			}
		};
	}
	
	@RequestMapping(value = "/my-tests", method = RequestMethod.GET)
	public Callable<String> getYourTests(HttpServletRequest request){
		
		return new Callable<String>(){
			public String call(){
				HttpSession session = request.getSession();
				if(session.getAttribute("profile") != null){
					int userID = ((Profile)session.getAttribute("profile")).getId();
					List<Test> userTests = testService.getUserTestsFromAPI(userID);
					request.setAttribute("tests", (List<Test>)userTests);
					return "user-tests";
				}
				else{
					return "redirect:http://localhost:8080/SocialSlack/user/login";
				}
			}
		};
	}
	
}
