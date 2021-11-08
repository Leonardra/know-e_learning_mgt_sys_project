package com.ehizman.know.service.mailService;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static com.ehizman.know.service.mailService.MailSenderConstants.*;

@Slf4j
@Service("mailgun")
public class MailGunEmailServiceImpl implements EmailService{

    @Override
    public MailResponse sendMailTo(@NotNull Message message){
        HttpResponse<JsonNode> request = null;
        try {
            request = Unirest.post(MAILGUN_API_HEADER+DOMAIN_NAME+"/messages")
                    .basicAuth("api", API_KEY)
                    .queryString("from", message.getFrom())
                    .queryString("to", message.getTo())
                    .queryString("subject", message.getSubject())
                    .queryString("text", message.getBody())
                    .asJson();
            log.info("response-->{}", request.getBody());
            return request.getStatus() == 200 ?
                    new MailResponse(true):new MailResponse(false);
        } catch (UnirestException e) {
            log.info("Exception occurred --> {}", e.getMessage());
            return new MailResponse(false);
        }
    }
}