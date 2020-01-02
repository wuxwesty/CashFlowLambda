package com.wuxwesty.services;

public class TransactionIdRequest {
    private int id;
    public TransactionIdRequest() {
    }
    public TransactionIdRequest(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
