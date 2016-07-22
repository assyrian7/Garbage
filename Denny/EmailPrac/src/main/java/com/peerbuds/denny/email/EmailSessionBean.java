package com.peerbuds.denny.email;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * Session Bean implementation class EmailSessionBean
 */
@Stateless(mappedName = "EmailSessionBean")
@LocalBean
public class EmailSessionBean {
	
	private int port = 465;
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
		switch (protocol) {
		    case SMTPS:
		        props.put("mail.smtp.ssl.enable", true);
		        break;
		    case TLS:
		        props.put("mail.smtp.starttls.enable", true);
		        break;
		        
		}
		Authenticator authenticator = null;
		if (auth) {
		    props.put("mail.smtp.auth", true);
		    authenticator = new Authenticator() {
		        private PasswordAuthentication pa = new PasswordAuthentication(username, password.toCharArray());
		        @Override
		        public PasswordAuthentication getPasswordAuthentication() {
		            return pa;
		        }
		    };
		}//endOf if
		Session session = Session.getInstance(props);
		session.setDebug(debug);
		
		MimeMessage message = new MimeMessage(session);
		try {
		    message.setFrom(new InternetAddress(from));
		    InternetAddress[] address = {new InternetAddress(to)};
		    message.setRecipients(Message.RecipientType.TO, address);
		    message.setSubject(subject);
		    message.setSentDate(new Date());
		    message.setText(body);
		    Transport.send(message);
		} catch (MessagingException ex) {
		    ex.printStackTrace();
		}

		
	}//endOf sendEmail
	
	
}
