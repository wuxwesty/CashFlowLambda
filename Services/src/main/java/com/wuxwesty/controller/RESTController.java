package com.wuxwesty.controller;

import com.wuxwesty.model.Account;
import com.wuxwesty.model.CashFlow;
import com.wuxwesty.model.Transaction;
import com.wuxwesty.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RESTController
{
    private @Autowired GetAccounts getAccounts;
    private @Autowired GetAccount getAccount;
    private @Autowired PostAccount postAccount;
    private @Autowired PutAccount putAccount;
    private @Autowired DeleteAccount deleteAccount;

    private @Autowired GetCashFlows getCashFlows;

    private @Autowired GetTransactions getTransactions;
    private @Autowired GetTransaction getTransaction;
    private @Autowired PostTransaction postTransaction;
    private @Autowired PutTransaction putTransaction;
    private @Autowired DeleteTransaction deleteTransaction;


    @GetMapping("/accounts")
    private @ResponseBody List<Account> getAccounts() {
        return getAccounts.apply(null);
    }

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable Long id) {
        Message<String> msg = MessageBuilder.withPayload(id.toString()).build();
        return getAccount.apply(msg).getPayload();
    }

    @RequestMapping(value="/account", method = RequestMethod.POST)
    public Account postAccount(@PathVariable AccountRequest account) {
        return postAccount.apply(account);
    }

    @RequestMapping(value="/account", method = RequestMethod.PUT)
    public Account putAccount(@PathVariable Account account) {
        return putAccount.apply(account);
    }

    @RequestMapping(value="/account", method = RequestMethod.DELETE)
    public Integer deleteAccount(@PathVariable AccountIdRequest account) {
        return deleteAccount.apply(account);
    }

    @GetMapping("/transactions")
    private @ResponseBody List<Transaction> getTransactions() {
        return getTransactions.apply(null);
    }

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
    public Transaction getTransaction(@PathVariable Long id) {
        Message<String> msg = MessageBuilder.withPayload(id.toString()).build();
        return getTransaction.apply(msg).getPayload();
    }

    @RequestMapping(value="/transaction", method = RequestMethod.POST)
    public Transaction postTransaction(@PathVariable TransactionRequest transaction) {
        return postTransaction.apply(transaction);
    }

    @RequestMapping(value="/transaction", method = RequestMethod.PUT)
    public Transaction putTransaction(@PathVariable Transaction transaction) {
        return putTransaction.apply(transaction);
    }

    @RequestMapping(value="/transaction", method = RequestMethod.DELETE)
    public Integer deleteTransaction(@PathVariable TransactionIdRequest transaction) {
        return deleteTransaction.apply(transaction);
    }

    @GetMapping("/cashflows")
    private @ResponseBody List<CashFlow> getCashflows() {
        return getCashFlows.apply(null);
    }

}
