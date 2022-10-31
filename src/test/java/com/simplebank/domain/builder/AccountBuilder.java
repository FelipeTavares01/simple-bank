package com.simplebank.domain.builder;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AccountBuilder {

    private boolean active;
    private int availableLimit;
    private List<Transaction> history = new ArrayList<>();

    public AccountBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public AccountBuilder availableLimit(int availableLimit) {
        this.availableLimit = availableLimit;
        return this;
    }

    public AccountBuilder addTransaction(Transaction transaction) {
        this.history.add(transaction);
        return this;
    }

    public AccountBuilder addTransactions(List<Transaction> transactions) {
        this.history = transactions;
        return this;
    }

    public Account build() {
        return new Account(this.active, this.availableLimit, this.history);
    }
}
