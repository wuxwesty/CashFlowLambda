package com.wuxwesty;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;

public class GetAccountsHandler implements RequestHandler<AccountsRequest, List<Account>> {
   public List<Account> handleRequest(AccountsRequest accountsRequest, Context context) {
        final CognitoIdentity identity = context.getIdentity();
        final String userID = identity.getIdentityId();
        Query q = new Query(context.getLogger());
        List<Account> list = q.getAllAccounts(userID, context.getLogger());
        return list;
    }
}
