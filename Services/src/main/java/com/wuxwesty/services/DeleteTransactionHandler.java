package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.wuxwesty.model.*;

public class DeleteTransactionHandler implements RequestHandler<TransactionIdRequest, Integer> {
   public Integer handleRequest(TransactionIdRequest trnsactionIdRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       q.deleteTransaction(userID, trnsactionIdRequest.getId(), context.getLogger());
       return 1;
    }
}
