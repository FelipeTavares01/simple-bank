package com.simplebank.domain.processor;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;

public interface IProcessor {

    void process(Account account, Transaction transaction);
}
