package com.ehizman.know.service.events;

import com.ehizman.know.data.model.LearningParty;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final LearningParty applicationUser;
    public OnRegistrationCompleteEvent(LearningParty source) {
        super(source);
        this.applicationUser = source;
    }
}
