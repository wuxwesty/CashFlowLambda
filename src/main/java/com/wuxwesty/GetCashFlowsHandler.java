package com.wuxwesty;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;

import java.util.List;

public class GetCashFlowsHandler implements RequestHandler<CashFlowRequest, List<CashFlow>> {
    public List<CashFlow> handleRequest(CashFlowRequest cashFlowRequest, Context context) {
        final CognitoIdentity identity = context.getIdentity();
        final String userID = identity.getIdentityId();
        Query q = new Query(context.getLogger());
        List<Account> aList = q.getAllAccounts(userID, context.getLogger());
        List<Transaction> tList = q.getAllTransactions(userID, context.getLogger());
        List<CashFlow> cfList = new CashFlows().Generate(aList, tList);
        return cfList;
    }
}



