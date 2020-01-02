package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.wuxwesty.model.*;

public class PostTransactionHandler implements RequestHandler<TransactionRequest, Transaction> {
   public Transaction handleRequest(TransactionRequest transactionRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       Transaction t = q.postTransaction(userID, transactionRequest.getTransaction(), context.getLogger());
       return t;
    }
}
