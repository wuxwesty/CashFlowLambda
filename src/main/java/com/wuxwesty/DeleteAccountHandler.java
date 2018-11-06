package com.wuxwesty;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteAccountHandler implements RequestHandler<AccountIdRequest, Integer> {
   public Integer handleRequest(AccountIdRequest accountIdRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       q.deleteAccount(userID, accountIdRequest.getId(), context.getLogger());
       return 1;
    }
}
