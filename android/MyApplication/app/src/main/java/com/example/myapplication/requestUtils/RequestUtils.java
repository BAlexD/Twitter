package com.example.myapplication.requestUtils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RequestUtils {
    private static final HttpClient httpClient = new DefaultHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String executeGet(HttpGet httpGet) throws IOException {
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }
    public static String executePost(String body, HttpPost httpPost) throws IOException {
        StringEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        return EntityUtils.toString(responseEntity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> splitJsonToList(String jsonArray) throws JsonProcessingException {
        final JsonNode jsonNode = objectMapper.readTree(jsonArray);
        return StreamSupport.stream(jsonNode.spliterator(), false)
                .map(JsonNode::toString)
                .collect(Collectors.toList());
    }
}
