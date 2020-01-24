package com.wuxwesty.services;

import org.springframework.stereotype.Component;

import java.util.function.Function;
import com.wuxwesty.dataaccess.Query;

@Component
public class DeleteTransaction implements Function<TransactionIdRequest, Integer> {
   @Override
   public Integer apply(TransactionIdRequest trnsactionIdRequest) {
       final String userID = "";
       Query q = new Query();
       q.deleteTransaction(userID, trnsactionIdRequest.getId());
       return 1;
    }
}
