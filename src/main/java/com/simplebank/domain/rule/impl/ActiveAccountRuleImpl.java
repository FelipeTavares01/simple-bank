package com.simplebank.domain.rule.impl;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.rule.IRule;
import com.simplebank.domain.rule.enums.ViolatedRuleEnum;

public class ActiveAccountRuleImpl implements IRule {

    @Override
    public String violatedRuleMessage() {
        return ViolatedRuleEnum.ACTIVE_ACCOUNT_RULE.getMessage();
    }

    @Override
    public boolean validate(Account account, Transaction transaction) {
        return account.isActive();
    }
}
