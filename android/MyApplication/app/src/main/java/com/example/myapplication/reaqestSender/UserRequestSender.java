package com.example.myapplication.reaqestSender;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UserRequestSender {
    private final HttpClient httpClient = new DefaultHttpClient();
    private final String userEndpoint = "http://10.0.2.2:8080/user";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void createUser(String body) throws IOException {
        HttpPost httpPost = new HttpPost(body);
        StringEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpClient.execute(httpPost);
    }
}
