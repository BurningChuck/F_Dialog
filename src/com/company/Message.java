package com.company;

import java.util.List;

public class Message {
    private String regex;
    private List<String> responses;

    public Message(String s, List<String> asList) {
        regex = s;
        responses = asList;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String pattern) {
        this.regex = pattern;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }
}