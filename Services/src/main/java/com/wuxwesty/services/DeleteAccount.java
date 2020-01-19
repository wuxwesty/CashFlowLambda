package com.wuxwesty.services;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DeleteAccount implements Function<AccountIdRequest, Integer> {
    @Override
    public Integer apply(AccountIdRequest accountIdRequest) {
        final String userID = "";
        Query q = new Query();
        q.deleteAccount(userID, accountIdRequest.getId());
        return 1;
    }
}
