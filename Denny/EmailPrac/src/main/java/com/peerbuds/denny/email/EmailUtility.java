package com.peerbuds.denny.email;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
public class EmailUtility {

	public static void sendEmail(String host, String port,
			final String userName, final String password, String toAddress,
			String subject, String messageBody) throws AddressException,
			MessagingException {

		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};

		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message message = new MimeMessage(session);
		
		MimeMultipart content = new MimeMultipart();
		
		//HTML part
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText("<html>" + messageBody + "</html>", "US-ASCII" ,"html");
		content.addBodyPart(textPart);
		
		
		message.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		message.setRecipients(Message.RecipientType.TO, toAddresses);
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setContent(content, "text/html");
		message.saveChanges();

		// sends the e-mail
	    Transport transport = session.getTransport("smtp");
	    transport.connect(host, userName, password);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();

	}
}
