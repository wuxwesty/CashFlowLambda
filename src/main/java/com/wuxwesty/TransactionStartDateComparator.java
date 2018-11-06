package com.wuxwesty;

import java.util.Comparator;

public class TransactionStartDateComparator implements Comparator<Transaction> {

    public int compare(Transaction first, Transaction second) {
        return (first.getStartDate().compareTo(second.getStartDate()));
    }
}