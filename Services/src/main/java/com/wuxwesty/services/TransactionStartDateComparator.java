package com.wuxwesty.services;

import java.util.Comparator;
import com.wuxwesty.model.*;

public class TransactionStartDateComparator implements Comparator<Transaction> {

    public int compare(Transaction first, Transaction second) {
        return (first.getStartDate().compareTo(second.getStartDate()));
    }
}