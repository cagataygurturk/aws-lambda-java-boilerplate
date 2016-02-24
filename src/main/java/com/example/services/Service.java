package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

    @Autowired
    AnotherService service2;

    public String getText(String text) {
        return service2.getText(text);
    }
}
