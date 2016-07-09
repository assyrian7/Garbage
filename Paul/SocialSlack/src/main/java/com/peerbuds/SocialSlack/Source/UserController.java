package com.peerbuds.SocialSlack.Source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
import com.peerbuds.SocialSlack.Forms.SignUpForm;

@Controller
@RequestMapping("/user")
public class UserController {


	private ProfileService profileService = new ProfileService();
	
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
	
	@RequestMapping(value = "/create-test", method = RequestMethod.GET)
	public String createTest(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		if(session.getAttribute("profile") != null){
			return "create-test";
		} 
		else{
			return "redirect:login";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String initLogin(Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		if(session.getAttribute("profile") != null){
			return "redirect:dashboard";
		}
		
		LoginForm form = new LoginForm();
		model.addAttribute("loginForm", form);
		return "login";
		
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String initSignUp(Model model, HttpServletRequest request){
		
		SignUpForm form = new SignUpForm();
		model.addAttribute("signUpForm", form);
		return "signup";
		
	}
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
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
					return "redirect:dashboard";
				}
			}
		};
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Callable<String> processLogin(@Valid LoginForm loginForm, BindingResult results, HttpServletRequest request){
		
		return new Callable<String>(){
			
			public String call(){
				
				HttpSession session = request.getSession();
				if(session.getAttribute("profile") != null){
					return "redirect:dashboard";
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
						return "redirect:dashboard";
					}
					else{
						
						request.setAttribute("result", "Username password combination doesn't match");
						return "login";
					}
					
				}
				
			}
			
		};
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		session.removeAttribute("profile");
		return "redirect:login";
		
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
