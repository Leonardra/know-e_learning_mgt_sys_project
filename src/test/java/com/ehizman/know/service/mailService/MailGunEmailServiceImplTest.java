package com.ehizman.know.service.mailService;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailGunEmailServiceImplTest {
    private EmailService emailService;

    @Autowired
    public MailGunEmailServiceImplTest(@Qualifier("mailgun") EmailService emailService) {
        this.emailService = emailService;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendMailTo(){
        Message message = Message.builder()
                .from("ehizman@semicolon.africa")
                .to("oziomaokoroafor@gmail.com")
                .subject("Hi there!")
                .body("Hey!, how are you doing")
                .build();
        MailResponse response = emailService.sendMailTo(message);
        assertTrue(response.isSuccessful());
    }
}