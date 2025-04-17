package org.bshg.demo.zmail.services;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendActivationEmail(String to, String fullname, String activationCode) throws MessagingException;

    @Async
    void sendForgetPasswordEmail(String to, String fullname, String forgetPasswordCode) throws MessagingException;
}
