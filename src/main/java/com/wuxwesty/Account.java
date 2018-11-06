package com.wuxwesty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

public class Account {

    private String userID;
    private int accountID;
    private String description;
    private int accountTypeID;
    private double amount;
    private double rate;
    private int sequence;

    public Account() {
    }

    public Account(String userID, int accountID, String description, int accountTypeID, double amount, double rate, int sequence) {
        this.userID = userID;
        this.accountID = accountID;
        this.description = description;
        this.accountTypeID = accountTypeID;
        this.amount = amount;
        this.rate = rate;
        this.sequence = sequence;
    }

    public Account(String json) {
        Gson gson = new Gson();
        Account request = gson.fromJson(json, Account.class);
        this.accountID = request.getAccountID();
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAccountID() { return accountID; }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAccountTypeID() {
        return accountTypeID;
    }

    public void setAccountTypeID(int accountTypeID) {
        this.accountTypeID = accountTypeID;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
