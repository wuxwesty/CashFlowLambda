package com.wuxwesty.services;

import com.wuxwesty.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PutTransaction implements Function<Transaction, Transaction> {
    @Override
    public Transaction apply(Transaction transactionRequest) {
        final String userID = "";
        Query q = new Query();
        Transaction t = q.putTransaction(userID, transactionRequest);
        return t;
    }
}
