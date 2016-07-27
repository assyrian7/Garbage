package com.peerbuds.SpringMVCExample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String renderIndex(HttpServletRequest request){
		/*
			Do something like
			String username = request.getParameter("username"); 
			String password = request.getParameter("password");
			
			OR EVEN
			HttpSession session = request.getSession();
		*/
		return "index";
	}
	
	
}
