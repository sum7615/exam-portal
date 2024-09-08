package com.exam.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromAddress;

    public void sendSimpleEmail(Set<String> toEmails, Set<String> ccEmails, String subject, String htmlBody) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart
            
            // Set to addresses
            for (String email : toEmails) {
                helper.addTo(email);
            }
            
            // Set cc addresses
            if (ccEmails != null && !ccEmails.isEmpty()) {
                for (String email : ccEmails) {
                    helper.addCc(email);
                }
            }
            
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indicates HTML
            
            helper.setFrom(fromAddress);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
