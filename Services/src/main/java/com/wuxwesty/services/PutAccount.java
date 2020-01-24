package com.wuxwesty.services;

import com.wuxwesty.model.Account;
import org.springframework.stereotype.Component;
import com.wuxwesty.dataaccess.Query;
import java.util.function.Function;

@Component
public class PutAccount implements Function<Account, Account> {
    @Override
    public Account apply(Account accountRequest) {
        final String userID = "";
        Query q = new Query();
        Account a = q.putAccount(userID, accountRequest);
        return a;
    }
}
