package com.simplebank.domain.processor.impl;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.processor.IProcessor;

public class DebitProcessorImpl implements IProcessor {

    @Override
    public void process(Account account, Transaction transaction) {
        account.debitLimit(transaction.getAmount());
        account.addTransaction(transaction);
    }
}
