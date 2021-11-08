package com.ehizman.know.service.mailService;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
public class Message {
    @Email
    private String to;
    @Email
    private String from;
    private String subject;
    private String body;
}
