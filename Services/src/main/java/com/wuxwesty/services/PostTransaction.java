package com.wuxwesty.services;

import com.wuxwesty.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostTransaction implements Function<TransactionRequest, Transaction> {
    @Override
    public Transaction apply(TransactionRequest transactionRequest) {
        final String userID = "";
        Query q = new Query();
        Transaction t = q.postTransaction(userID, transactionRequest.getTransaction());
        return t;
    }
}
