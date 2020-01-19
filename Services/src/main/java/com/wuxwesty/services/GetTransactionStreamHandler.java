package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.wuxwesty.model.Account;
import com.wuxwesty.model.Transaction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

//import jdk.nashorn.internal.parser.JSONParser;

// HELP: https://www.baeldung.com/aws-lambda-api-gateway

public class GetTransactionStreamHandler implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
        throws IOException {

        final CognitoIdentity identity = context.getIdentity();
        final String userID = identity.getIdentityId();
        Query q = new Query();
        //Account a = q.getAccount(userID, accountIdRequest.getId(), context.getLogger());

        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();

        //AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        //DynamoDB dynamoDb = new DynamoDB(client);

        try {
            JSONObject event = (JSONObject) parser.parse(reader);

            if (event.get("body") != null) {
                Account account = new Account((String) event.get("body"));

                //dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                //        .putItem(new PutItemSpec().withItem(new Item().withNumber("id", person.getId())
                //                .withString("name", person.getName())));
            }

            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "New item created");

            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-header", "my custom header value");

            responseJson.put("statusCode", 200);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());

        } catch (ParseException pex) {
            responseJson.put("statusCode", 400);
            responseJson.put("exception", pex);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();

    }

    public void handleGetByParam(
            InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();

        JSONObject headerJson = new JSONObject();
        headerJson.put("Access-Control-Allow-Origin", "*");
        headerJson.put("Access-Control-Allow-Credentials", true);
        responseJson.put("headers", headerJson);

        final CognitoIdentity identity = context.getIdentity();
        final String userID = identity.getIdentityId();
        Query q = new Query();

        //AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        //DynamoDB dynamoDb = new DynamoDB(client);

        //Item result = null;
        Transaction t = null;
        try {

            JSONObject event = (JSONObject) parser.parse(reader);
            JSONObject responseBody = new JSONObject();

            if (event.get("pathParameters") != null) {
                JSONObject pps = (JSONObject) event.get("pathParameters");
                if (pps.get("id") != null) {
                    int id = Integer.parseInt((String) pps.get("id"));
                    //result = dynamoDb.getTable(DYNAMODB_TABLE_NAME).getItem("id", id);
                    t = q.getTransaction(userID, id);
                }
            }
            else if (event.get("pathParameters") != null) {
                JSONObject pps = (JSONObject) event.get("pathParameters");
                if (pps.get("id") != null) {
                    int id = Integer.parseInt((String) pps.get("id"));
                    //result = dynamoDb.getTable(DYNAMODB_TABLE_NAME).getItem("id", id);
                    t = q.getTransaction(userID, id);
                }
            }

            if (t != null) {
                //responseBody.put("Transaction", t.toJson());
                //responseJson.put("body", responseBody.toString());
                responseJson.put("body", t.toJson());
                responseJson.put("statusCode", 200);
            } else {
                responseBody.put("message", "No item found");
                responseJson.put("statusCode", 404);
                responseJson.put("body", responseBody.toString());
            }


        } catch (ParseException pex) {
            responseJson.put("statusCode", 400);
            responseJson.put("exception", pex);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
    }}
