package com.example.myapplication.reaqestSender;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.entities.Post;
import com.example.myapplication.requestUtils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

public class PostRequestSender {
    private final HttpClient httpClient = new DefaultHttpClient();
    private final String postEndpoint = "http://10.0.2.2:8080/post";
    private final String userEndpoint = "http://10.0.2.2:8080/user";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Post createPost(String body) throws IOException {
        HttpPost httpPost = new HttpPost(postEndpoint);
        RequestUtils.executePost(body, httpPost);
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Post> getAllPost() throws IOException {
        HttpGet httpGet = new HttpGet(postEndpoint);
        String posts = RequestUtils.executeGet(httpGet);
        List<String> postStrings = RequestUtils.splitJsonToList(posts);
        System.out.println(postStrings);
        for (String post : postStrings) {
            ObjectNode objectNode = objectMapper.readValue(post, ObjectNode.class);
            String user_id = objectNode.get("userId").toString();
            httpGet = new HttpGet(userEndpoint + "/" + user_id);
            String user = RequestUtils.executeGet(httpGet);
            System.out.println(user);
        }
        return null;
    }

}
