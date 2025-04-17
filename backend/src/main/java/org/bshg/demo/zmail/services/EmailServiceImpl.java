package org.bshg.demo.zmail.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.bshg.demo.zmail.enums.EmailTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${app.mailing.from}")
    private String emailFrom;
    @Value("${app.auth.activation.url}")
    private String activationUrl;
    @Value("${app.mail.auth.activation.subject}")
    private String activationSubject;
    @Value("${app.auth.forget-password.url}")
    private String resetPasswordUrl;
    @Value("${app.mail.auth.forget-password.subject}")
    private String resetPasswordSubject;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendActivationEmail(
            String to,
            String fullname,
            String activationCode
    ) throws MessagingException {
        Map< String, Object> model = new HashMap<>();
        model.put("fullname", fullname);
        model.put("activationUrl", activationUrl + "/" + activationCode);

        sendEmail(to, EmailTemplate.ACTIVATE_ACCOUNT, activationSubject, model);
    }

    @Override
    @Async
    public void sendForgetPasswordEmail(
            String to,
            String fullname,
            String forgetPasswordCode
    ) throws MessagingException {
        Map< String, Object> model = new HashMap<>();
        model.put("fullname", fullname);
        model.put("resetPasswordUrl", resetPasswordUrl + "/" + forgetPasswordCode);

        sendEmail(to, EmailTemplate.FORGET_PASSWORD, resetPasswordSubject, model);
    }

    @Async
    public void sendEmail(
            String to,
            EmailTemplate emailTemplate,
            String Subject,
            Map< String, Object> model
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Context context = new Context();
        context.setVariables(model);

        helper.setFrom(emailFrom);
        helper.setTo(to);
        helper.setSubject(Subject);

        String template = templateEngine.process(emailTemplate.template, context);
        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }
}
