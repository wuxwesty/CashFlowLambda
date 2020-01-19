package com.wuxwesty.services;

import com.wuxwesty.model.Account;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostAccount implements Function<AccountRequest, Account> {
    @Override
    public Account apply(AccountRequest accountRequest) {
        final String userID = "";
        Query q = new Query();
        Account a = q.postAccount(userID, accountRequest.getAccount());
        return a;
    }
}
