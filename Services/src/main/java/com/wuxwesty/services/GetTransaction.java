package com.wuxwesty.services;

import com.wuxwesty.dataaccess.Query;
import com.wuxwesty.model.Account;
import com.wuxwesty.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

// https://masteringaws.gulden.consulting/2019/01/spring-cloud-function-aws.html
// https://cloud.spring.io/spring-cloud-static/spring-cloud-function/3.0.0.M1/spring-cloud-function.html

@Component("getTransaction")
public class GetTransaction implements Function<Message<String>, Message<Transaction>> {
    static final Logger logger = LoggerFactory.getLogger(GetTransaction.class);

    @Override
    public Message<Transaction> apply(Message<String> transactionIdRequest) {
        final String userID = "";
        System.out.println(transactionIdRequest.getHeaders().toString());
        try {
            JSONParser parser = new JSONParser();
            String requestString = transactionIdRequest.getHeaders().get("request").toString();
            System.out.println(requestString);
            requestString = requestString.substring(0, requestString.length()-2) + "}";
            System.out.println(requestString);
            JSONObject pps = (JSONObject) parser.parse(requestString);
            System.out.println(pps.toJSONString());
            int id = Integer.parseInt((String) pps.get("id"));
            Query q = new Query();
            Transaction a = q.getTransaction(userID, id );
            Message<Transaction> message
                    = MessageBuilder.withPayload(a)
                    .setHeader("contentType", "application/json").build();
            return message;
        } catch (ParseException pex) {
            System.out.println("Error: Could not parse parameters");
        }
        return null;
    }
}