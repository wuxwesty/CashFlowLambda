package com.wuxwesty;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PutTransactionHandler implements RequestHandler<TransactionRequest, Transaction> {
   public Transaction handleRequest(TransactionRequest transactionRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       Transaction t = q.putTransaction(userID, transactionRequest.getTransaction(), context.getLogger());
       return t;
    }
}
