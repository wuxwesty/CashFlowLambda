package com.wuxwesty.services;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    @Bean
//    public Function<String, String> reverseString() {
//        return value -> new StringBuilder(value).reverse().toString();
//    }

    /*
    @Bean
    public Function<CashFlowRequest, List<CashFlow>> GetCashFlows() {
        return value ->
        {
            String userID = "";
            Query q = new Query();
            logger.info("Getting Accounts");
            List<Account> aList = q.getAllAccounts(userID);
            logger.info("Getting Transactions");
            List<Transaction> tList = q.getAllTransactions(userID);
            logger.info("Calculating Cashflows");
            List<CashFlow> cfList = new CashFlows().Generate(aList, tList);
            logger.info("Done Cashflows");
            return cfList;
        };
    }
    */

    
}