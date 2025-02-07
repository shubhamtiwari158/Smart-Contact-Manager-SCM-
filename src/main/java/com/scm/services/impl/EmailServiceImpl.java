package com.scm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;  // Use 'emailSender' for clarity

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    @Override
    public void sendEmail(String to, String subject, String body) {
        // Create a SimpleMailMessage object
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(domainName);  // Set the 'from' address using domainName
        emailSender.send(message);  // Send the email
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO: Implement sending emails with HTML content
        throw new UnsupportedOperationException("Method 'sendEmailWithHtml' not implemented yet.");
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO: Implement sending emails with attachments
        
        throw new UnsupportedOperationException("Method 'sendEmailWithAttachment' not implemented yet.");
    }
}
