package com.simplebank.domain.service;

import com.simplebank.domain.builder.AccountBuilder;
import com.simplebank.domain.builder.TransactionBuilder;
import com.simplebank.domain.exception.RuleViolatedException;
import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.processor.impl.DebitProcessorImpl;
import com.simplebank.domain.rule.IRule;
import com.simplebank.domain.rule.enums.ViolatedRuleEnum;
import com.simplebank.domain.rule.impl.ActiveAccountRuleImpl;
import com.simplebank.domain.rule.impl.AvailableLimitRuleImpl;
import com.simplebank.domain.rule.impl.TransactionLimitRuleImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private List<IRule> rules;
    private TransactionService authorizerService;

    @BeforeEach
    void setup() {
        this.rules = Arrays.asList(
                new ActiveAccountRuleImpl(),
                new AvailableLimitRuleImpl(),
                new TransactionLimitRuleImpl()
        );

        this.authorizerService = new TransactionService(rules, new DebitProcessorImpl());
    }

    @Test
    public void shouldProcessTransaction_When_NoRuleWereViolated() {

        Transaction transaction = new TransactionBuilder().amount(100).build();

        Account account = new AccountBuilder().active(true).availableLimit(200).build();

        boolean expected = true;
        boolean actual = authorizerService.validateAndProcess(account, transaction);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ThrowException_When_ViolateAvailableLimitRule() {

        Transaction transaction = new TransactionBuilder().amount(100).build();

        Account account = new AccountBuilder().active(true).availableLimit(50).build();

        RuleViolatedException exception = assertThrows(RuleViolatedException.class,
                () -> authorizerService.validateAndProcess(account, transaction));

        String expected = ViolatedRuleEnum.AVALIABLE_LIMIT_RULE.getMessage();
        String actual = exception.getMessages().get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ThrowException_When_ViolateActiveAccountRule() {

        Transaction transaction = new TransactionBuilder().amount(100).build();
        Account account = new AccountBuilder().active(false).availableLimit(100).build();

        RuleViolatedException exception = assertThrows(RuleViolatedException.class,
                () -> authorizerService.validateAndProcess(account, transaction));

        String expected = ViolatedRuleEnum.ACTIVE_ACCOUNT_RULE.getMessage();
        String actual = exception.getMessages().get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ThrowException_When_ViolateTransactionLimitRule() {

        Transaction transaction = new TransactionBuilder()
                .amount(10000)
                .time(LocalDateTime.now())
                .build();

        Account account = new AccountBuilder()
                .active(true)
                .availableLimit(50000)
                .addTransaction(transaction)
                .build();

        RuleViolatedException exception = assertThrows(RuleViolatedException.class,
                () -> authorizerService.validateAndProcess(account, transaction));

        String expected = ViolatedRuleEnum.TRANSACTION_LIMIT_RULE.getMessage();
        String actual = exception.getMessages().get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void should_ThrowException_When_ViolatedAllRules() {

        Transaction transaction = new TransactionBuilder()
                .amount(6000)
                .time(LocalDateTime.now())
                .build();

        Account account = new AccountBuilder()
                .active(false)
                .availableLimit(500)
                .addTransaction(transaction)
                .build();

        RuleViolatedException exception = assertThrows(RuleViolatedException.class,
                () -> authorizerService.validateAndProcess(account, transaction));

        List<String> expected = Arrays.asList(
                ViolatedRuleEnum.ACTIVE_ACCOUNT_RULE.getMessage(),
                ViolatedRuleEnum.AVALIABLE_LIMIT_RULE.getMessage(),
                ViolatedRuleEnum.TRANSACTION_LIMIT_RULE.getMessage()
        );

        List<String> actual = exception.getMessages();

        assertTrue(actual.containsAll(expected));
    }
}
