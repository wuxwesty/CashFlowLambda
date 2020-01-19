package com.wuxwesty.services;

import java.util.List;
import java.util.function.Function;

import com.wuxwesty.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class GetTransactions implements Function<TransactionsRequest, List<Transaction>> {
    @Override
    public List<Transaction> apply(TransactionsRequest transactionsRequest) {
        final String userID = "";
        Query q = new Query();
        List<Transaction> list = q.getAllTransactions(userID);
        return list;
    }
}
