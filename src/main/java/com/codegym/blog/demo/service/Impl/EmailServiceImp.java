package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.service.Interface.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String body, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("prohubxc0920i1@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
    }
}
