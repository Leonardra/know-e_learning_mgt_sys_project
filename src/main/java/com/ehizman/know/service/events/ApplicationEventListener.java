package com.ehizman.know.service.events;

import com.ehizman.know.service.mailService.EmailService;
import com.ehizman.know.service.mailService.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Component
public class ApplicationEventListener {
    private EmailService emailService;
    TemplateEngine templateEngine;

    @Autowired
    public ApplicationEventListener(EmailService emailService, TemplateEngine templateEngine) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }

    @EventListener
    public void handleRegistrationCompleteEvent
            (OnRegistrationCompleteEvent onRegistrationCompleteEvent){
        confirmRegistration(onRegistrationCompleteEvent);

    }
    private void confirmRegistration(OnRegistrationCompleteEvent event){
        String verificationToken = UUID.randomUUID().toString();
        Context ctx = new Context();
        ctx.setVariable("verificationToken", verificationToken);
        Message message = Message.builder()
                                .from("ehizman.tutoredafrica@gmail.com")
                                .to(event.getApplicationUser().getEmail())
                                .subject("CONFIRMATION EMAIL")
                                .body(templateEngine.process("registration_verification_mail.html",ctx))
                                .build();
        emailService.sendMail(message);
    }
}
