package com.simplebank.domain.rule.impl;

import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.rule.IRule;
import com.simplebank.domain.rule.enums.ViolatedRuleEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionLimitRuleImpl implements IRule {

    public static final long MAXIMUM_NUMBER_TRANSACTION_PER_DAY = 5;
    public static final int MAXIMUM_TRANSACTION_AMOUNT_PER_DAY = 1000;

    @Override
    public String violatedRuleMessage() {
        return ViolatedRuleEnum.TRANSACTION_LIMIT_RULE.getMessage();
    }

    @Override
    public boolean validate(Account account, Transaction newTransaction) {
        LocalDate currentDate = LocalDateTime.now().toLocalDate();

        int totalAmount = account.getHistory().stream()
                .filter(transaction -> transaction.getTime().toLocalDate().isEqual(currentDate))
                .map(Transaction::getAmount)
                .reduce(0, Integer::sum);

        long transactionNumberOfTheDay = account.getHistory().stream()
                .filter(transaction -> transaction.getTime().toLocalDate().isEqual(currentDate))
                .count();

        return transactionNumberOfTheDay <= MAXIMUM_NUMBER_TRANSACTION_PER_DAY
                && totalAmount <= MAXIMUM_TRANSACTION_AMOUNT_PER_DAY;
    }
}
