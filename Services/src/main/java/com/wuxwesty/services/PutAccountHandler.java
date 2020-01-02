package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.wuxwesty.model.*;

//public class PutAccountHandler implements RequestHandler<AccountRequest, Account> {
public class PutAccountHandler implements RequestHandler<Account, Account> {
    //public Account handleRequest(AccountRequest accountRequest, Context context) {
    public Account handleRequest(Account accountRequest, Context context) {
       final CognitoIdentity identity = context.getIdentity();
       final String userID = identity.getIdentityId();
       Query q = new Query(context.getLogger());
       Account a = q.putAccount(userID, accountRequest, context.getLogger());
       return a;
    }
}
