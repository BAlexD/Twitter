package com.example.myapplication.requestSender;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.requestUtils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PostRequestSender {
    private final HttpClient httpClient = new DefaultHttpClient();
    private final String postEndpoint = "http://92.63.107.176:8080/post";
    private final String userEndpoint = "http://92.63.107.176:8080/user";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     Метод добавляет пост
     /post
     json body:  {
     "profileId": 0,
     "subscriberId": "string"
     }
     */
    public Post createPost(String body) throws IOException {
        HttpPost httpPost = new HttpPost(postEndpoint);
        //String encodedBody = URLEncoder.encode(body, "UTF-8");
        RequestUtils.executePost(body, httpPost);
        return null;
    }

    /**
     Метод возвращает посты по id пользователя
     /post/user/{id}
     */
    //TODO личные посты пользователя
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Collection<Post> getUsersPosts(String body) throws IOException{
        String encodedBody = URLEncoder.encode(body, "UTF-8");
        HttpGet httpGet = new HttpGet(postEndpoint + "/user/" + encodedBody);
        String posts = RequestUtils.executeGet(httpGet);
        List<Post> postsColl = getPostsFromString(posts);
        return postsColl;
    }

    /**
     Метод возвращает посты подписок по id пользователя
     /post/subscribes/{id}
     */
    //TODO посты пользователя по подпискам
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Collection<Post> getSubscribersPosts(String body) throws IOException{
        String encodedBody = URLEncoder.encode(body, "UTF-8");
        HttpGet httpGet = new HttpGet(postEndpoint + "/subscribes/" + encodedBody);
        String posts = RequestUtils.executeGet(httpGet);
        List<Post> postsColl = getPostsFromString(posts);
        return postsColl;
    }

    /**
     Метод возвращает все посты
     /post
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Collection<Post> getAllPost() throws IOException {
        HttpGet httpGet = new HttpGet(postEndpoint);
        String posts = RequestUtils.executeGet(httpGet);
        List<Post> postsColl = getPostsFromString(posts);
        return postsColl;
    }

    /**
     Метод удаляет пост по id
     /post/{id}
     */
    //TODO удаление поста
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Post deletePost(String body) throws IOException{
        String encodedBody = URLEncoder.encode(body, "UTF-8");
        HttpDelete httpDelete = new HttpDelete(postEndpoint + "/" + encodedBody);
        RequestUtils.executeDelete(httpDelete);
        return null;
    }

    /**
     Метод возвращает пост по id
     /post/{id}
     */
    //TODO изменить количество комментариев и лайков
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Post getPost(String id) throws IOException {
        String encodedBody = URLEncoder.encode(id, "UTF-8");
        HttpGet httpGet = new HttpGet(postEndpoint+ "/" + encodedBody);
        String post = RequestUtils.executeGet(httpGet);
        ObjectNode objectNode = objectMapper.readValue(post, ObjectNode.class);

        String user_id = objectNode.get("userId").toString();
        UserRequestSender userRequestSender = new UserRequestSender();
        User user_ = userRequestSender.getUser(user_id);

        CommentRequestSender commentRequestSender = new CommentRequestSender();
        Long comments = commentRequestSender.getCommentsCount(Long.parseLong(objectNode.get("id").toString()));

        LikeRequestSender likesRequestSender = new LikeRequestSender();
        Long likes = likesRequestSender.getLikesCount(objectNode.get("id").toString());

        String content = objectNode.get("content").toString();

        return new Post(Long.parseLong(objectNode.get("id").toString()), user_, content.substring(1, content.length()-1), comments,likes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Post> getPostsFromString(String posts) throws IOException {
        List<Post> postsColl = new ArrayList<>();
        List<String> postStrings = RequestUtils.splitJsonToList(posts);
        for (int i = postStrings.size() - 1; i >=0; i--) {
            String post = postStrings.get(i);
            ObjectNode objectNode = objectMapper.readValue(post, ObjectNode.class);

            String user_id = objectNode.get("userId").toString();
            UserRequestSender userRequestSender = new UserRequestSender();
            User user_ = userRequestSender.getUser(user_id);

            CommentRequestSender commentRequestSender = new CommentRequestSender();
            Long comments = commentRequestSender.getCommentsCount(Long.parseLong(objectNode.get("id").toString()));

            LikeRequestSender likesRequestSender = new LikeRequestSender();
            Long likes = likesRequestSender.getLikesCount(objectNode.get("id").toString());

            String content = objectNode.get("content").toString();
            //String content = objectNode.get("content").toString();

            Post post_ = new Post(Long.parseLong(objectNode.get("id").toString()), user_, content.substring(1, content.length()-1),comments,likes);
            postsColl.add(post_);
        }
        //Collections.reverse(postsColl);
        return postsColl;
    }
}
