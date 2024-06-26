package com.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendMail(String toMail, String text, String Subject) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom("chitrakshgupta.2003@gmail.com");
		mailMessage.setTo(toMail);
		mailMessage.setText(text);
		mailMessage.setSubject(Subject);
		
		javaMailSender.send(mailMessage);
		
	}
}
