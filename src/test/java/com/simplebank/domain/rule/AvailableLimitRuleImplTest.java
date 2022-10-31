package com.simplebank.domain.rule;

import com.simplebank.domain.builder.AccountBuilder;
import com.simplebank.domain.builder.TransactionBuilder;
import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.rule.impl.AvailableLimitRuleImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvailableLimitRuleImplTest {

    private IRule rule;

    @BeforeEach
    void setup() {
        this.rule = new AvailableLimitRuleImpl();
    }

    @Test
    public void should_ReturnTrue_When_TransactionAmountIsLessThanTheAccountLimit() {
        Transaction transaction = new TransactionBuilder().amount(100).build();
        Account account = new AccountBuilder().availableLimit(200).build();

        boolean expected = true;
        boolean actual = this.rule.validate(account, transaction);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnFalse_When_NoLimitAvailable() {
        Transaction transaction = new TransactionBuilder().amount(100).build();
        Account account = new AccountBuilder().availableLimit(50).build();

        boolean expected = false;
        boolean actual = this.rule.validate(account, transaction);

        assertEquals(expected, actual);
    }
}
