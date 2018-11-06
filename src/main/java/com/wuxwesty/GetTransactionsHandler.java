package com.wuxwesty;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTransactionsHandler implements RequestHandler<TransactionsRequest, List<Transaction>> {
   public List<Transaction> handleRequest(TransactionsRequest transactionsRequest, Context context) {
        final CognitoIdentity identity = context.getIdentity();
        final String userID = identity.getIdentityId();
        Query q = new Query(context.getLogger());
        List<Transaction> list = q.getAllTransactions(userID, context.getLogger());
        return list;
    }
}
