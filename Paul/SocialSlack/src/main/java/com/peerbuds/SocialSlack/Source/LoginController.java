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

import com.peerbuds.SocialSlack.Forms.LoginForm;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private QuestionService questionService = new QuestionService();
	private ProfileService profileService = new ProfileService();
	
	@RequestMapping(method = RequestMethod.GET)
	public String initLogin(Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		if(session.getAttribute("profile") != null){
			return "redirect:user/dashboard";
		}
		
		LoginForm form = new LoginForm();
		model.addAttribute("loginForm", form);
		return "login";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Callable<String> processLogin(@Valid LoginForm loginForm, BindingResult results, HttpServletRequest request){
		
		return new Callable<String>(){
			
			public String call(){
				
				HttpSession session = request.getSession();
				if(session.getAttribute("profile") != null){
					return "redirect:user/dashboard";
				}
				
				if(results.hasErrors()){
					return "login";
				}
				
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				Profile profile = profileService.getProfileByUsername(username);
				
				if(profile == null){
					
					request.setAttribute("result", "Profile not found for that username");
					return "login";
					
				}
				else{
					
					password = Encrypt.hash(password.toCharArray(), (Salt.salt + username).getBytes(), password.length(), Encrypt.KEY_LENGTH);
					
					if(password.equals(profile.getPassword())){
						
						session.setAttribute("profile", profile);
						session.setMaxInactiveInterval(30 * 60);
						return "redirect:user/dashboard";
					}
					else{
						
						request.setAttribute("result", "Username password combination doesn't match");
						return "login";
					}
					
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
