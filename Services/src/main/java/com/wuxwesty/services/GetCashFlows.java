package com.wuxwesty.services;

import com.wuxwesty.dataaccess.Query;
import com.wuxwesty.functions.CashFlows;
import com.wuxwesty.model.Account;
import com.wuxwesty.model.CashFlow;
import com.wuxwesty.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.function.Function;
import java.util.List;

@Component("getCashFlows")
public class GetCashFlows implements Function<List<String>, List<CashFlow>> {
    static final Logger logger = LogManager.getLogger(GetCashFlows.class);

    //public class GetCashFlowsHandler implements RequestHandler<CashFlowRequest, List<CashFlow>> {
    //public List<CashFlow> handleRequest(CashFlowRequest cashFlowRequest, Context context) {

    @Override
    public List<CashFlow> apply(final List<String> cashFlowRequest) {
        String userID = "";
        Query query = new Query();
        logger.info("Getting Accounts");
        List<Account> aList = query.getAllAccounts(userID);
        logger.info("Getting Transactions");
        List<Transaction> tList = query.getAllTransactions(userID);
        logger.info("Calculating Cashflows");
        List<CashFlow> cfList = new CashFlows().Generate(aList, tList);
        logger.info("Done Cashflows");
        return cfList;
    }

}



