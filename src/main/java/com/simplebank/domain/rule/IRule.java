package com.simplebank.domain.rule;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;

public interface IRule {

    String violatedRuleMessage();

    boolean validate(Account account, Transaction transaction);

}
