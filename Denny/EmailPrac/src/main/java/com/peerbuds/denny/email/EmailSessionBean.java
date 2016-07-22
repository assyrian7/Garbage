package com.peerbuds.denny.email;

import java.util.Date;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Session Bean implementation class EmailSessionBean
 */
@Stateless(mappedName = "EmailSessionBean")
@LocalBean
public class EmailSessionBean {
	
	private int port = 587;
	private String host = "smtp.gmail.com";
	private String from = "charlie.zaa88@gmail.com";
	private boolean auth = true;
	private String username = "charlie.zaa88@gmail.com";
	private String password = "protractor2016";
	private Protocol protocol = Protocol.SMTPS;
	private boolean debug = true;

	public void sendEmail(String to, String subject, String body)
	{	
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.auth", true);
		
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication pa = new PasswordAuthentication(username, password);
		    public PasswordAuthentication getPasswordAuthentication() {
		    	return pa;
		    };
		};
		
		Session session = Session.getInstance(props, authenticator);
		session.setDebug(debug);
		
		MimeMessage message = new MimeMessage(session);
		try {
		    message.setFrom(new InternetAddress(from));
		    InternetAddress[] address = { new InternetAddress(to) };
		    message.setRecipients(Message.RecipientType.TO, address);
		    message.setSubject(subject);
		    message.setSentDate(new Date());
		    message.setText(body);
		    Transport transport = session.getTransport("smtp");
		    transport.connect(host, username, password);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		} catch (MessagingException ex) {
			throw new RuntimeException (ex);
		}
	}//endOf sendEmail
	
	
}
