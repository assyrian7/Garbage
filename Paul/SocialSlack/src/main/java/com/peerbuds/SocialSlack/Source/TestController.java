package com.peerbuds.SocialSlack.Source;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {

	private TestService testService = new TestService();
	
	@RequestMapping(value = "/{testID}", method = RequestMethod.GET)
	public String accessTest(@PathParam("testID") int testID, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		if(session.getAttribute("profile") != null){
			return "view_test";
		}
		else{
			return "redirect:login";
		}
	}
	
}
