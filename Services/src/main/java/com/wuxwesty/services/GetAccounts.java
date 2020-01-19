package com.wuxwesty.services;

import java.util.List;
import java.util.function.Function;

import com.wuxwesty.model.Account;
import org.springframework.stereotype.Component;

@Component
public class GetAccounts implements Function<AccountsRequest, List<Account>> {
    @Override
    public List<Account> apply(AccountsRequest accountsRequest) {
        final String userID = "";
        Query q = new Query();
        List<Account> list = q.getAllAccounts(userID);
        return list;
    }
}
