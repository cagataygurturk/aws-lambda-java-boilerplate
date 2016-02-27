package com.cagataygurturk.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

    /**
     * Autowiring another Spring Bean
     */
    @Autowired
    AnotherService anotherService;

    public String getText(String text) {
        return anotherService.getText(text);
    }
}
