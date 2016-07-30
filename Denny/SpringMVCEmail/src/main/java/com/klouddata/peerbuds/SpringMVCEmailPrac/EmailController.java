package com.klouddata.peerbuds.SpringMVCEmailPrac;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.*;

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
 
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public String sendWithAttach(ModelMap model,
			@ModelAttribute("mail") EmailInfo mailInfo) {
		try {
			final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
			MimeMessage message = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			//get only username to show
			String frEmail = ms.getUsername();
			mailInfo.setFrom(frEmail);
		
			helper.setFrom(new InternetAddress(null, "Peerbuds Registration"));			
			helper.setTo(mailInfo.getTo());
			//helper.setReplyTo(mailInfo.getFrom()); //if any
			helper.setSubject(mailInfo.getSubject());
			
			//Set true for html.
			helper.setText(mailInfo.getBody(), true);
			
			mailSender.send(message);
		} catch (Exception ex) {
			model.addAttribute("error", "ERROR " + ex.getMessage());
			return "EmailForm";
		}
		return "Result";
	}
 

}
