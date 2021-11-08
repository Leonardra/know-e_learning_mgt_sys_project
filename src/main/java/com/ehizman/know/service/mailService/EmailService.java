package com.ehizman.know.service.mailService;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public interface EmailService {
    MailResponse sendMailTo(@NotNull Message message);
}
