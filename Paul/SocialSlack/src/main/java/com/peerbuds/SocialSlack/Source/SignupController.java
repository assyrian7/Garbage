package com.peerbuds.SocialSlack.Source;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.peerbuds.SocialSlack.Forms.SignUpForm;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

	private QuestionService questionService = new QuestionService();
	private ProfileService profileService = new ProfileService();
	
	@RequestMapping(method = RequestMethod.GET)
	public String initSignUp(Model model, HttpServletRequest request){
		
		SignUpForm form = new SignUpForm();
		model.addAttribute("signUpForm", form);
		return "signup";
		
	}
	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> processSignUp(@Valid SignUpForm signupForm, BindingResult results, HttpServletRequest request){
		
		return new Callable<String>(){
			
			public String call(){
				
				HttpSession session = request.getSession();
				
				if(results.hasErrors()){
					return "signup";
				}
				
				Profile profile = new Profile();
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				password = Encrypt.hash(password.toCharArray(), (Salt.salt + username).getBytes(), password.length(), Encrypt.KEY_LENGTH);
				profile.setUsername(username);
				profile.setPassword(password); 
				profile.setFirstname(request.getParameter("firstname"));
				profile.setLastname(request.getParameter("lastname"));
				profile.setEmail(request.getParameter("email"));
				profile.setId(profile.hashCode());
				String result = profileService.signup("http://localhost:8080/ProfileService/rest/ProfileService/profiles/signup", profile);
				String a = "";
				
				if(result.equals("Username already used")){
					
				    request.setAttribute("result", result);
					return "signup";
					
				} else if(result.equals("Email already used")){
					
					request.setAttribute("result", result);
					return "signup";
					
				} else {
					
					if(session.getAttribute("profile") != null){
						session.removeAttribute("profile");
					}
					profile = profileService.getProfileByUsername(username);
					session.setAttribute("profile", profile);
					session.setMaxInactiveInterval(30 * 60);
					return "redirect:user/dashboard";
				}
			}
		};
		
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
	
	
}
