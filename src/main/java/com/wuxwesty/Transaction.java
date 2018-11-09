package com.wuxwesty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String userID;
    private int transactionID;
    private int accountID;
    private int fromAccountID;
    private int activityTypeID;
    private Date startDate;
    private Date endDate;
    private int recurrenceTypeID;
    private int dayOfWeek;
    private int month;
    private int dayOfMonth;
    private String description;
    private double amount;

    public Transaction() {
    }

    public Transaction(String userID, int transactionID, int accountID, int fromAccountID, int activityTypeID,
        Date startDate, Date endDate, int recurrentTypeID, int dayOfWeek, int month, int dayOfMonth,
        String description, double amount) {
        this.userID = userID;
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.fromAccountID = fromAccountID;
        this.activityTypeID = activityTypeID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurrenceTypeID = recurrentTypeID;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.description = description;
        this.amount = amount;
    }

    public Transaction(Transaction t) {
        this.setUserID(t.getUserID());
        this.setTransactionID(t.getTransactionID());
        this.setAccountID(t.getAccountID());
        this.setFromAccountID(t.getFromAccountID());
        this.setActivityTypeID(t.getActivityTypeID());
        this.setStartDate(t.getStartDate());
        this.setEndDate(t.getEndDate());
        this.setRecurrenceTypeID(t.getRecurrenceTypeID());
        this.setDayOfWeek(t.getDayOfWeek());
        this.setMonth(t.getMonth());
        this.setDayOfMonth(t.getDayOfMonth());
        this.setDescription(t.getDescription());
        this.setAmount(t.getAmount());
    }

    public Transaction(String json) {
        Gson gson = new Gson();
        Transaction request = gson.fromJson(json, Transaction.class);
        this.transactionID = request.getTransactionID();
    }

    // HELP: Date Format https://stackoverflow.com/questions/6873020/gson-date-format
    public String toJson() {
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(this);
    }


    public String getUserID() { return userID; }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getFromAccountID() {
        return fromAccountID;
    }

    public void setFromAccountID(int fromAccountID) {
        this.fromAccountID = fromAccountID;
    }

    public int getActivityTypeID() {
        return activityTypeID;
    }

    public void setActivityTypeID(int activityTypeID) {
        this.activityTypeID = activityTypeID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String convertDateToString(Date myDate) { return new SimpleDateFormat("yyyy-MM-dd").format(myDate);}

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRecurrenceTypeID() {
        return recurrenceTypeID;
    }

    public void setRecurrenceTypeID(int recurrenceTypeID) {
        this.recurrenceTypeID = recurrenceTypeID;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description;    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) { this.amount = amount; }
}
