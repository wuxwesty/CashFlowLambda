package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.wuxwesty.model.*;

public class PutTransactionHandler implements RequestHandler<Transaction, Transaction> {
   public Transaction handleRequest(Transaction transactionRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       Transaction t = q.putTransaction(userID, transactionRequest, context.getLogger());
       return t;
    }
}
