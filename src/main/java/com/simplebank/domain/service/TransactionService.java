package com.simplebank.domain.service;

import com.simplebank.domain.exception.RuleViolatedException;
import com.simplebank.domain.model.Account;
import com.simplebank.domain.model.Transaction;
import com.simplebank.domain.processor.IProcessor;
import com.simplebank.domain.rule.IRule;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {

    private List<IRule> rules;
    private IProcessor processor;

    public TransactionService(List<IRule> rules, IProcessor processor) {
        this.rules = rules;
        this.processor = processor;
    }

    public boolean validateAndProcess(Account account, Transaction transaction) {
        validateRules(account, transaction);

        this.processor.process(account, transaction);

        return true;
    }

    private void validateRules(Account account, Transaction transaction) {

        List<String> violatedRules = getViolatedRules(account, transaction);

        if(existsViolatedRules(violatedRules)) {
            throw new RuleViolatedException(violatedRules);
        }
    }

    private List<String> getViolatedRules(Account account, Transaction transaction) {
        return this.rules.stream()
                .filter(rule -> this.violatedRules(account, transaction, rule))
                .map(rule -> rule.violatedRuleMessage())
                .collect(Collectors.toList());
    }

    private boolean violatedRules(Account account, Transaction transaction, IRule rule) {
        return !rule.validate(account, transaction);
    }

    private boolean existsViolatedRules(List<String> violatedRules) {
        return !violatedRules.isEmpty();
    }

}
