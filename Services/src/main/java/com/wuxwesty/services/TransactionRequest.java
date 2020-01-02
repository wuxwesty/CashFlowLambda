package com.wuxwesty.services;

import com.wuxwesty.model.*;

public class TransactionRequest {
    public TransactionRequest() { }
    private Transaction transaction;
    public TransactionRequest(Transaction transaction) {
        this.transaction = transaction;
    }
    public Transaction getTransaction() {
        return this.transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
