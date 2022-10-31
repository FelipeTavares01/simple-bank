package com.simplebank.domain.rule;

import com.simplebank.domain.builder.AccountBuilder;
import com.simplebank.domain.builder.TransactionBuilder;
import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.rule.impl.ActiveAccountRuleImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActiveAccountRuleImplTest {

    private IRule rule;

    @BeforeEach
    void setup() {
        this.rule = new ActiveAccountRuleImpl();
    }

    @Test
    public void should_ReturnTrue_When_ActiveAccount() {
        Transaction transaction = new TransactionBuilder().build();
        Account activeAccount = new AccountBuilder().active(true).build();

        boolean expected = true;
        boolean actual = this.rule.validate(activeAccount, transaction);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnFalse_When_InactiveAccount() {
        Transaction transaction = new TransactionBuilder().build();
        Account inactiveAccount = new AccountBuilder().active(false).build();

        boolean expected = false;
        boolean actual = this.rule.validate(inactiveAccount, transaction);

        assertEquals(expected, actual);
    }
}
