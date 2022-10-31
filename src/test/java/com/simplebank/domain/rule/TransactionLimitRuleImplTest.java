package com.simplebank.domain.rule;

import com.simplebank.domain.builder.AccountBuilder;
import com.simplebank.domain.builder.TransactionBuilder;
import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.rule.impl.TransactionLimitRuleImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TransactionLimitRuleImplTest {

    private IRule rule;

    @BeforeEach
    void setup() {
        this.rule = new TransactionLimitRuleImpl();
    }

    @Test
    public void should_ReturnTrue_When_TransactionLimitIsValid() {
        List<Transaction> transactions = Arrays.asList(
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build()
        );

        Account account = new AccountBuilder().addTransactions(transactions).build();

        boolean expected = true;
        boolean actual = this.rule.validate(account, transactions.get(0));

        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnFalse_When_ExceedTheNumberTransactionsPerDay() {
        List<Transaction> transactions = Arrays.asList(
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build()
        );

        Account account = new AccountBuilder().addTransactions(transactions).build();

        boolean expected = false;
        boolean actual = this.rule.validate(account, transactions.get(0));

        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnFalse_When_ExceedTheAmountAllowedPerDay() {
        Transaction transaction = new TransactionBuilder().amount(1100).time(LocalDateTime.now()).build();

        Account account = new AccountBuilder().addTransaction(transaction).build();

        boolean expected = false;
        boolean actual = this.rule.validate(account, transaction);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnTrue_When_PassListTransactionsFromTheSameDay() {
        List<Transaction> transactions = Arrays.asList(
                new TransactionBuilder().amount(500).time(LocalDateTime.now().minusDays(1)).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now().minusDays(1)).build(),
                new TransactionBuilder().amount(300).time(LocalDateTime.now().minusDays(1)).build(),
                new TransactionBuilder().amount(900).time(LocalDateTime.now().plusDays(1)).build(),
                new TransactionBuilder().amount(50).time(LocalDateTime.now().plusDays(1)).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build(),
                new TransactionBuilder().amount(100).time(LocalDateTime.now()).build()
        );

        Account account = new AccountBuilder().addTransactions(transactions).build();

        boolean expected = true;
        boolean actual = this.rule.validate(account, transactions.get(0));

        assertEquals(expected, actual);
    }

}
