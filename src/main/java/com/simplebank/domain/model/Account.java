package com.simplebank.domain.model;

import java.util.Collections;
import java.util.List;

public class Account {

    private boolean active;
    private int availableLimit;
    private List<Transaction> history;

    public Account(boolean active, int availableLimit, List<Transaction> history) {
        this.active = active;
        this.availableLimit = availableLimit;
        this.history = history;
    }

    public boolean isActive() {
        return this.active;
    }

    public int getAvailableLimit() {
        return this.availableLimit;
    }

    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(this.history);
    }

    public void debitLimit(int amount) {
        this.availableLimit -= amount;
    }

    public void addTransaction(Transaction transaction) {
        this.history.add(transaction);
    }

}
