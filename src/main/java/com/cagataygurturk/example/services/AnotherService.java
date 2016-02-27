package com.cagataygurturk.example.services;

import org.springframework.stereotype.Component;

@Component
public class AnotherService {

    public String getText(String text) {
        return "Hi " + text;
    }
}
