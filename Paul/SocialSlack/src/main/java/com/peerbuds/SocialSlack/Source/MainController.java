package com.peerbuds.SocialSlack.Source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.peerbuds.SocialSlack.Forms.LoginForm;
import com.peerbuds.SocialSlack.Forms.SignUpForm;

@Controller
public class MainController{
 
	private QuestionService questionService = new QuestionService();
	private ProfileService profileService = new ProfileService();
	
	
	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	    model.addAttribute("message", "Hello Spring MVC Framework!");
	 
	    request.setAttribute("item", "helllllo");
	      
	    return "hello";
	}
	   
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
	      
		String token = request.getParameter("token");
		
	    return "index";
	}
	   
	/*
	@RequestMapping(value="/", method = RequestMethod.POST)
	public String pIndex(RedirectAttributes attributes, HttpServletRequest request, HttpServletResponse response){
		   
		
	    String action = request.getParameter("action");
	    request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
	    AsyncContext asyncCtx = request.startAsync();
		asyncCtx.addListener(new AppAsyncListener());
		ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");
		AsyncRequestProcessor processor = new AsyncRequestProcessor(asyncCtx, action);
		executor.execute(processor);
		attributes.addFlashAttribute("result", AsyncRequestProcessor.getResult());
		//request.setAttribute("result", "result");
		if(action.equals("getTest")){
			return "redirect:listquestions";
		}
		return "redirect:result";
		
	}
	*/
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public Callable<String> pIndex(RedirectAttributes attributes, HttpServletRequest request){
		   
		return new Callable<String>() {
			
			public String call(){
				
				String action = request.getParameter("action");
				
				if(action.equals("updateProfile")){
					Profile profile = new Profile();
					profile.setUsername(request.getParameter("username"));
					profile.setPassword(request.getParameter("password")); 
					profile.setFirstname(request.getParameter("firstname"));
					profile.setLastname(request.getParameter("lastname"));
					profile.setId(Integer.parseInt(request.getParameter("id")));
					profile.setEmail(request.getParameter("email"));
					
					String result = profileService.updateProfile("http://localhost:8080/ProfileService/rest/ProfileService/profiles", profile);
			
					attributes.addFlashAttribute("result", result);
					//request.setAttribute("result", "result");
					
					return "redirect:result";
				} else if(action.equals("getTest")){
					int testID = Integer.parseInt(request.getParameter("testID"));
		    		String data = getURL("http://localhost:8080/TestRestService/rest/QuestionService/questions/" + testID);
					
		    		List<Question> questions = questionService.getQuestionsFromAPI(data);
		    		
		    		//int i = questions.get(0).getQuestionID();
		    		
		    		attributes.addFlashAttribute("questions", questions);
		    		
		    		return "redirect:listquestions";
		    		
				} else{
					return "";
				}
		
	
		
			}
		};
	}
	
	
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
	public String profileDashboard(HttpServletRequest request){
	
		HttpSession session = request.getSession();
		if(session.getAttribute("profile") != null){
			request.setAttribute("profile", session.getAttribute("profile"));
			return "dashboard";
		}
		else{
			return "redirect:login";
		}
		
	}
	@RequestMapping(value="/result", method = RequestMethod.GET)
	public String result(RedirectAttributes attributes, ModelMap model, HttpServletRequest request){
	    
		Map<String, ?> params = attributes.getFlashAttributes();
		
		request.setAttribute("result", getResult((String)params.get("result")));
		return "result";
	}
	
	@RequestMapping(value="/listquestions", method = RequestMethod.GET)
	public String listQuestions(RedirectAttributes attributes, ModelMap model, HttpServletRequest request){
		
		Map<String, ?> params = attributes.getFlashAttributes();
		
		request.setAttribute("questions", (List<Question>)params.get("questions"));
		
		return "listquestions";
		
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
