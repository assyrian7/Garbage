package com.klouddata.peerbuds.SpringMVCEmailPrac;

import java.util.UUID;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.klouddata.peerbuds.entity.EmailInfo;
import com.klouddata.peerbuds.SimpleEncodeDecode.SimpleEncodeDecode;

@Controller
public class EmailController {

	@Autowired
	ServletContext context;
	@Autowired
	JavaMailSender mailSender;
 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		model.addAttribute("mail", new EmailInfo());
		return "EmailForm";
	}
	
	//Should take user details as parameters
	private String getToken()
	{
		String passphrase = "password";
		String token = SimpleEncodeDecode.encrypt(UUID.randomUUID().toString(), passphrase);
		
		return token;
	}
 
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public String createResetEmail(HttpServletRequest request, ModelMap model,
			@ModelAttribute("mail") EmailInfo mailInfo) {
		try {
			final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
			MimeMessage message = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			String frEmail = ms.getUsername();
			mailInfo.setFrom("no-reply@peerbuds-registration.com");
			
			String token = getToken();
			String contextPath = "http://" + request.getServerName() + 
				      ":" + request.getServerPort() + 
				      request.getContextPath();
			String url = contextPath + "/user/changePassword?id=" + mailInfo.getTo() + "&token=" + token;
		
			helper.setFrom(new InternetAddress(null, "Peerbuds Registration"));			
			helper.setTo(mailInfo.getTo());
			//helper.setReplyTo(mailInfo.getFrom()); //if any
			helper.setSubject("Reset your password");
			
			//Set true for html.
			helper.setText("<html><head><title>Success</title></head>"
					+ "<body><p>To reset your password, "
					+ "please click the link provided: "
					+ "<a href="+ url +">Reset your password</a></body></html>", true);
			
			mailSender.send(message);
		} catch (Exception ex) {
			model.addAttribute("error", "ERROR " + ex.getMessage());
			return "EmailForm";
		}
		return "Result";
	}

}
