package com.wuxwesty.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.wuxwesty.model.Account;
import com.wuxwesty.model.CashFlow;
import com.wuxwesty.model.Transaction;

public class CashFlows {

    private Logger log = Logger.getLogger("GetCashFlows");

    public CashFlows() {}

    public List<CashFlow> Generate(List<Account> aList, List<Transaction> tList) {
        List<CashFlow> cfList = new ArrayList<CashFlow>();
        double[] amountArray = new double[aList.size()];
        List<Transaction> projectedArray = new ArrayList<Transaction>();

        // populate running amount array
        int i = 0;
        for(Account a : aList){
            amountArray[i] = a.getAmount();
            i++;
        }

        // initialize cashflow with current positions
        cfList.add( createCashFlow(new Date(), "Openning", false, 0, amountArray) );

        // Project transactions out until end of next month
        Date dateStart = new Date();
        log.info(String.format("dateStart: %tF", dateStart));
        Date dateEnd = new Date();
        dateEnd = new Date(dateEnd.getYear(), dateEnd.getMonth() + 12, 1, 0, 0, 0);
        dateEnd = new Date(dateEnd.getYear(), dateEnd.getMonth(), 0, 0, 0, 0);
        log.info(String.format("dateEnd: %tc", dateEnd));
        for(Transaction t : tList) {
            log.info(String.format("RecurrentType: %d", t.getRecurrenceTypeID()));
            if (t.getRecurrenceTypeID() == 1) { // once
                projectedArray.add(t);
            }
            if (t.getRecurrenceTypeID() == 2) { // weekly
                Date d = t.getStartDate();
                int addDays = (d.getDay() < t.getDayOfWeek()) ? t.getDayOfWeek() - d.getDay() : 7 - d.getDay();
                d = new Date(d.getYear(), d.getMonth(), d.getDay() + addDays, 0, 0, 0);
                while (d.compareTo(dateEnd) <= 0 && d.compareTo(t.getEndDate()) <= 0) {
                    Transaction t2 = new Transaction(t);
                    t2.setStartDate(d);
                    if (d.compareTo(dateStart) >= 0) {
                        projectedArray.add(t2);
                    }
                    d = new Date(d.getYear(), d.getMonth(), d.getDate() + 7, 0, 0, 0);
                }
            }
            if (t.getRecurrenceTypeID() == 3) { // biweekly
                Date d = t.getStartDate();
                int addDays = (d.getDay() < t.getDayOfWeek()) ? t.getDayOfWeek() - d.getDay() : 14 - d.getDay();
                d = new Date(d.getYear(), d.getMonth(), d.getDay() + addDays, 0, 0, 0);
                log.fine(String.format("d3: %tc %tc %tc", d, dateEnd, t.getEndDate()));
                log.fine(String.format("d3 check: %d %d", d.compareTo(dateEnd), d.compareTo(t.getEndDate())));
                while (d.compareTo(dateEnd) <= 0 && d.compareTo(t.getEndDate()) <= 0) {
                    Transaction t2 = new Transaction(t);
                    t2.setStartDate(d);
                    if (d.compareTo(dateStart) >= 0) {
                        projectedArray.add(t2);
                    }
                    d = new Date(d.getYear(), d.getMonth(), d.getDate() + 14, 0, 0, 0);
                    log.fine(String.format("d3: %tc", d));
                    log.fine(String.format("d3 check: %d %d", d.compareTo(dateEnd), d.compareTo(t.getEndDate())));
                }
            }
            if (t.getRecurrenceTypeID() == 4) { // monthly
                if (t.getDayOfMonth() <= 0) t.setDayOfMonth(1); // safety check
                Date d = t.getStartDate();
                int addMonths = (d.getDay() < t.getDayOfMonth()) ? 0 : 1;
                d = new Date(d.getYear(), d.getMonth() + addMonths, t.getDayOfMonth() , 0, 0, 0);
                while (d.compareTo(dateEnd) <= 0 && d.compareTo(t.getEndDate()) <= 0) {
                    Transaction t2 = new Transaction(t);
                    t2.setStartDate(d);
                    if (d.compareTo(dateStart) >= 0) {
                        projectedArray.add(t2);
                    }
                    d = new Date(d.getYear(), d.getMonth() + 1, t.getDayOfMonth(), 0, 0, 0);
                }
            }
            if (t.getRecurrenceTypeID() == 5) { // annually
                if (t.getDayOfMonth() <= 0) t.setDayOfMonth(1); // safety check
                Date d = t.getStartDate();
                int addYears = (d.getMonth() < t.getMonth()) ? 0 : 1;
                if (addYears == 0 && d.getMonth() == t.getMonth() && d.getDate() > t.getDayOfMonth()) {
                    addYears = 1;
                }
                d = new Date(d.getYear() + addYears, t.getMonth(), t.getDayOfMonth() , 0, 0, 0);
                while (d.compareTo(dateEnd) <= 0 && d.compareTo(t.getEndDate()) <= 0) {
                    Transaction t2 = new Transaction(t);
                    t2.setStartDate(d);
                    if (d.compareTo(dateStart) >= 0) {
                        projectedArray.add(t2);
                    }
                    d = new Date(d.getYear()+1, t.getMonth(), t.getDayOfMonth(), 0, 0, 0);
                }
            }

        }

        // Project month end values
        Date d = new Date(dateStart.getYear(), dateStart.getMonth() + 1, 0, 0, 0, 0);
        while (d.compareTo(dateEnd) <= 0) {
            Transaction t = new Transaction();
            t.setStartDate(d);
            t.setActivityTypeID(4);
            t.setDescription(String.format("%tB", d));
            projectedArray.add(t);
            d = new Date(d.getYear(), d.getMonth() + 2, 0, 0, 0, 0);
        }


        Collections.sort(projectedArray, new TransactionStartDateComparator());

        // create cash flow positions from projected transactions
        log.info(String.format("Projected Count: %d", projectedArray.size()));
        for (Transaction t : projectedArray) {
            int idFrom = -1;
            int idTo = -1;
            // Add to or Subtract from "To" account
            for (int j = 0; j < aList.size() && j < 5; j++) {
                if (aList.get(j).getAccountID() == t.getAccountID()) {
                    idTo = j;
                    if (t.getActivityTypeID() == 2) { // Expense
                        amountArray[j] = amountArray[j] - t.getAmount();
                    } else { // Income or Transfer
                        amountArray[j] = amountArray[j] + t.getAmount();
                    }
                }
            }
            // Subtract from "From" account
            for (int j = 0; j < aList.size() && j < 5; j++) {
                if (aList.get(j).getAccountID() == t.getFromAccountID()) {
                    idFrom = j;
                    amountArray[j] = amountArray[j] - t.getAmount();
                }
            }
            double amount = 0;
            if (t.getActivityTypeID() == 4) { // Month End
                cfList.add(createCashFlow(t.getStartDate(), t.getDescription(), true, amount, amountArray));
            } else {
                if (t.getActivityTypeID() == 1) {
                    amount = t.getAmount();
                } else {
                    amount = -t.getAmount();
                }
                cfList.add(createCashFlow(t.getStartDate(), t.getDescription(), false, amount, amountArray));
            }
        }

        return cfList;
    }

    private CashFlow createCashFlow(Date date, String description, Boolean isSubTotal, double amount, double[] amounts) {
        CashFlow cf = new CashFlow();
        cf.setStartDate(date);
        cf.setDescription(description);
        cf.setSubTotal(isSubTotal);
        cf.setAmount(amount);
        cf.setAmount1(amounts[0]);
        cf.setAmount2(amounts[1]);
        cf.setAmount3(amounts[2]);
        cf.setAmount4(amounts[3]);
        cf.setAmount5(amounts[4]);
        return cf;
    }
}



