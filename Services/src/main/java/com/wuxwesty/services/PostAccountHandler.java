package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.wuxwesty.model.*;

public class PostAccountHandler implements RequestHandler<AccountRequest, Account> {
   public Account handleRequest(AccountRequest accountRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       Account a = q.postAccount(userID, accountRequest.getAccount(), context.getLogger());
       return a;
    }
}
