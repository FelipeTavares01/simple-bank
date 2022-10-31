package com.simplebank.domain.model;

import java.time.LocalDateTime;

public class Transaction {

    private int amount;
    private String merchant;
    private LocalDateTime time;

    public Transaction(int amount, String merchant, LocalDateTime time) {
        this.amount = amount;
        this.merchant = merchant;
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public LocalDateTime getTime() {
        return time;
    }

}
