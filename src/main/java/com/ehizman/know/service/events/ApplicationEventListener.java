package com.ehizman.know.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {

    @EventListener
    public void handleRegistrationCompleteEvent
            (OnRegistrationCompleteEvent onRegistrationCompleteEvent){
        confirmRegistration();

    }
    private void confirmRegistration(){

    }
}
