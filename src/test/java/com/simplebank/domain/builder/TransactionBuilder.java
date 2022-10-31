package com.simplebank.domain.builder;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;

import java.time.LocalDateTime;

public class TransactionBuilder {

    private int amount;
    private String merchant;
    private LocalDateTime time;

    public TransactionBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder merchant(String merchant) {
        this.merchant = merchant;
        return this;
    }

    public TransactionBuilder time(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public Transaction build() {
        return new Transaction(this.amount, this.merchant, this.time);
    }
}
