package com.simplebank.domain.rule.enums;

public enum ViolatedRuleEnum {

    ACTIVE_ACCOUNT_RULE("Active account rule was violated."),
    TRANSACTION_LIMIT_RULE("Transaction limit rule was violated."),
    AVALIABLE_LIMIT_RULE("Available limit rule was violated.");

    private String message;

    ViolatedRuleEnum(String name) {
        this.message = name;
    }

    public String getMessage() {
        return this.message;
    }
}
