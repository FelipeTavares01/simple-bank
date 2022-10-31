package com.simplebank.domain.exception;

import java.util.List;

public class RuleViolatedException extends RuntimeException {

    private List<String> messages;

    public RuleViolatedException(String message) {
        super(message);
    }

    public RuleViolatedException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

}
