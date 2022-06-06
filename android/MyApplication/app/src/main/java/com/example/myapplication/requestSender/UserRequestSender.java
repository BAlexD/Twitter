package com.example.myapplication.requestSender;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.requestUtils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRequestSender {
    private final HttpClient httpClient = new DefaultHttpClient();
    private final String userEndpoint = "http://92.63.107.176:8080/user";
    //TODO поменять по серверу
    private final String userAutorizeEndpoint = "http://92.63.107.176:8080/user/authorize";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     Метод создает пользователя
     /user
     json body:  {
                 "login": "string",
                 "password": "string",
                 "firstName": "string",
                 "lastName": "string",
                 "email": "string",
                 "phone": "string"
                 }
     */
    public User createUser(String body) throws IOException {
        HttpPost httpPost = new HttpPost(userEndpoint);
       // String encodedBody = URLEncoder.encode(body, "UTF-8");

        String user = RequestUtils.executePost(body, httpPost);
        ObjectNode objectNodeUser = objectMapper.readValue(user, ObjectNode.class);

        SubscribeRequestSender subscribeRequestSender = new SubscribeRequestSender();
        Long subscribes = subscribeRequestSender.getSubscribesCount(Long.parseLong(objectNodeUser.get("id").toString()));
        Long subscribers = subscribeRequestSender.getSubscribersCount(Long.parseLong(objectNodeUser.get("id").toString()));

        String login = objectNodeUser.get("login").toString();

        User user_ = new User(Long.parseLong(objectNodeUser.get("id").toString()), login.substring(1, login.length()-1),
                subscribes, subscribers);
        return user_;
    }

    /**
     Метод возвращает пользоватлей по совпадению логина
     /user/search?filter={login}
     */
    //TODO получить пользователей поиском
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Collection<User> getUsers(String body) throws IOException {
        String encodedBody = URLEncoder.encode(body, "UTF-8");
        HttpGet httpGet = new HttpGet(userEndpoint + "/search?filter=" + encodedBody);
        String users = RequestUtils.executeGet(httpGet);
        List<User> users_ = new ArrayList<>();
        List<String> usersStrings = RequestUtils.splitJsonToList(users);
        for (String user : usersStrings) {
            ObjectNode objectNode = objectMapper.readValue(user, ObjectNode.class);

            SubscribeRequestSender subscribeRequestSender = new SubscribeRequestSender();
            Long subscribes = subscribeRequestSender.getSubscribesCount(Long.parseLong(objectNode.get("id").toString()));
            Long subscribers = subscribeRequestSender.getSubscribersCount(Long.parseLong(objectNode.get("id").toString()));

            String login = objectNode.get("login").toString();

            User user_ = new User(Long.parseLong(objectNode.get("id").toString()), login.substring(1, login.length()-1),
                    subscribes,subscribers);
            users_.add(user_);
        }

        return users_;
    }

    /**
     Метод возвращает пользователя по логину и паролю
     /user/authorize?login={login}&password={password}
     */
    //TODO авторизация пользователя
    public User getLoginUser(String login, String password)throws IOException{
        String encodedLogin = URLEncoder.encode(login, "UTF-8");
        String encodedPassword = URLEncoder.encode(password, "UTF-8");
        HttpGet httpGet = new HttpGet(userAutorizeEndpoint + "?login=" + encodedLogin +"&password="+ encodedPassword);
        String user = RequestUtils.executeGet(httpGet);
        ObjectNode objectNodeUser = objectMapper.readValue(user, ObjectNode.class);
        User user_;
        try{
            SubscribeRequestSender subscribeRequestSender = new SubscribeRequestSender();
            Long subscribes = subscribeRequestSender.getSubscribesCount(Long.parseLong(objectNodeUser.get("id").toString()));
            Long subscribers = subscribeRequestSender.getSubscribersCount(Long.parseLong(objectNodeUser.get("id").toString()));

            String login_ = objectNodeUser.get("login").toString();

            user_ = new User(Long.parseLong(objectNodeUser.get("id").toString()), login_.substring(1, login_.length()-1),
                    subscribes,subscribers);
        }catch(Exception e){
            return null;
        }

        return user_;
    }


    /**
     Метод возвращает пользователя по id
     /user/{id}
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public User getUser(String id) throws IOException {
        String encodedBody = URLEncoder.encode(id, "UTF-8");
        HttpGet httpGet = new HttpGet(userEndpoint+ "/" + encodedBody);
        String user = RequestUtils.executeGet(httpGet);
        ObjectNode objectNodeUser = objectMapper.readValue(user, ObjectNode.class);
        User user_;
        try {
            SubscribeRequestSender subscribeRequestSender = new SubscribeRequestSender();
            Long subscribes = subscribeRequestSender.getSubscribesCount(Long.parseLong(objectNodeUser.get("id").toString()));
            Long subscribers = subscribeRequestSender.getSubscribersCount(Long.parseLong(objectNodeUser.get("id").toString()));

            String login = objectNodeUser.get("login").toString();

            user_ = new User(Long.parseLong(objectNodeUser.get("id").toString()), login.substring(1, login.length()-1),
                    subscribes, subscribers);
        }catch(Exception e){
            return null;
        }
        return user_;
    }
}
