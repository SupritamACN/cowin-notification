package com.help.cowin.util.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.help.cowin.config.MailConfig;
import com.help.cowin.util.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{


	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
    MailConfig mailConfig;
	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	@Override
	public void sendEmail(String toEmail, String subject, String body){
		

		final String fromEmail = mailConfig.getMailid(); //requires valid gmail id
		final String password = mailConfig.getPassword(); // correct password for gmail id
		

		System.out.println("SSLEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", mailConfig.getSmtphost()); //SMTP Host
		props.put("mail.smtp.socketFactory.port", mailConfig.getSmtpport()); //SSL Port
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		props.put("mail.smtp.auth", mailConfig.getSmtpauth()); //Enabling SMTP Authentication
		props.put("mail.smtp.port", mailConfig.getSmtpport()); //SMTP Port

		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		Session session = Session.getDefaultInstance(props, auth);
		log.info("Session created");
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

	      msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      log.info("Message is ready");
    	  Transport.send(msg);  

	      log.info("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
		  log.error(e.getMessage());
	    }
	}
}
