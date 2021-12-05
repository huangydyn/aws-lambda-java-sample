package com.huangydyn;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class BookHandler implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();
        try {
            JSONObject event = (JSONObject) parser.parse(reader);
            JSONObject responseBody = new JSONObject();

            if (event.get("pathParameters") != null) {
                JSONObject pps = (JSONObject) event.get("pathParameters");
                if (pps.get("id") != null) {
                    int id = Integer.parseInt((String) pps.get("id"));
                    Book book = new Book(id, "path查询", DateTime.now());
                    responseBody.put("id", book.getId());
                    responseBody.put("name", book.getName());
                    responseBody.put("createdAt", book.getCreatedAt());

                } else {
                    Book book = new Book(111111, "非path查询", DateTime.now());
                    responseBody.put("id", book.getId());
                    responseBody.put("name", book.getName());
                    responseBody.put("createdAt", book.getCreatedAt());
                }
                JSONObject headerJson = new JSONObject();
                headerJson.put("x-custom-header", "hello");

                responseJson.put("statusCode", 200);
                responseJson.put("headers", headerJson);
                responseJson.put("body", responseBody.toString());
            } else {
                responseJson.put("statusCode", 400);
                responseJson.put("exception", "id is missed");
            }

        } catch (Exception pex) {
            responseJson.put("statusCode", 500);
            responseJson.put("exception", pex);
        }
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
    }
}
