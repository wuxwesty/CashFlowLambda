package com.wuxwesty;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAccountHandler implements RequestHandler<AccountIdRequest, Account> {
   public Account handleRequest(AccountIdRequest accountIdRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       Account a = q.getAccount(userID, accountIdRequest.getId(), context.getLogger());
       return a;
    }
}
