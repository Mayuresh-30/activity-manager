package com.activityManager.user.service.impl;

import com.activityManager.user.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    @Slf4j
    public void sendEmail(String to, String name) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);

            message.setSubject("Welcome to Our Platform");

            message.setText(
                    "Hello " + name +
                            ",\n\nWelcome to our platform.");

            log.info("email is ready to send ...");

            javaMailSender.send(message);
            log.info("email is sended successfully to {}", to);
        } catch (Exception e) {
            log.error("Mail sending failed: {}", e.getMessage());
            // TODO: handle exception
        }
    }
}
