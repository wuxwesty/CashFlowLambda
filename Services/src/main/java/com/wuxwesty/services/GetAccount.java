package com.wuxwesty.services;

import com.wuxwesty.dataaccess.Query;
import com.wuxwesty.model.Account;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.function.Function;

// https://masteringaws.gulden.consulting/2019/01/spring-cloud-function-aws.html
// https://cloud.spring.io/spring-cloud-static/spring-cloud-function/3.0.0.M1/spring-cloud-function.html

@Component("getAccount")
public class GetAccount implements Function<Message<String>, Message<Account>> {
    static final Logger logger = LogManager.getLogger(GetAccount.class);

    @Override
    public Message<Account> apply(Message<String> accountIdRequest) {
        final String userID = "";
        System.out.println(accountIdRequest.getHeaders().toString());
        try {
            JSONParser parser = new JSONParser();
            String requestString = accountIdRequest.getHeaders().get("request").toString();
            System.out.println(requestString);
            requestString = requestString.substring(0, requestString.length()-2) + "}";
            System.out.println(requestString);
            JSONObject pps = (JSONObject) parser.parse(requestString);
            System.out.println(pps.toJSONString());
            int id = Integer.parseInt((String) pps.get("id"));
            Query q = new Query();
            Account a = q.getAccount(userID, id );
            Message<Account> message
                    = MessageBuilder.withPayload(a)
                    .setHeader("contentType", "application/json").build();
            return message;
        } catch (ParseException pex) {
            System.out.println("Error: Could not parse parameters");
        }
        return null;
    }
}