package com.simplebank.domain.processor;

import com.simplebank.domain.builder.AccountBuilder;
import com.simplebank.domain.builder.TransactionBuilder;
import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.processor.impl.DebitProcessorImpl;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DebitProcessorImplTest {

    private IProcessor processor;

    @BeforeEach
    void setup() {
        this.processor = new DebitProcessorImpl();
    }

    @Test
    public void should_DebitLimitAnAmountOf100_When_TransactionAmountEqual100() {
        Transaction transaction = new TransactionBuilder().amount(100).build();
        Account account = new AccountBuilder().availableLimit(100).build();

        this.processor.process(account, transaction);

        int expected = 0;
        int actual = account.getAvailableLimit();

        assertEquals(expected, actual);
    }

    @Test
    public void should_addTwoTransactions_When_CallProcessorTwice() {
        List<Transaction> transactions = Arrays.asList(
                new TransactionBuilder().build(),
                new TransactionBuilder().build()
        );

        Account account = new AccountBuilder().build();

        transactions.stream().forEach(transaction -> this.processor.process(account, transaction));

        int expected = 2;
        int actual = account.getHistory().size();

        assertEquals(expected, actual);
    }
}
