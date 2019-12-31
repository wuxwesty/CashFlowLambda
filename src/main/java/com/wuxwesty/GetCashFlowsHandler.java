package com.wuxwesty;

import java.time.Instant;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.List;

public class GetCashFlowsHandler implements RequestHandler<CashFlowRequest, List<CashFlow>> {
    public List<CashFlow> handleRequest(CashFlowRequest cashFlowRequest, Context context) {
        LambdaLogger logger = context.getLogger();
        CognitoIdentity identity = context.getIdentity();
        //final String userID = identity.getIdentityId();
        String userID = "";
        Query q = new Query(context.getLogger());
        log(logger, "Getting Accounts");
        List<Account> aList = q.getAllAccounts(userID, context.getLogger());
        log(logger,"Getting Transactions");
        List<Transaction> tList = q.getAllTransactions(userID, context.getLogger());
        log(logger,"Calculating Cashflows");
        List<CashFlow> cfList = new CashFlows().Generate(aList, tList);
        log(logger,"Done Cashflows");
        return cfList;
    }

    private void log(LambdaLogger logger, String msg) {
        logger.log(Instant.now().toString() + "|"+ msg);
    }
}



