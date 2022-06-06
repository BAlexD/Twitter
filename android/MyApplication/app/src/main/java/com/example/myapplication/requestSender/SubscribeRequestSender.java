package com.example.myapplication.requestSender;

import com.example.myapplication.entities.Subscribe;
import com.example.myapplication.requestUtils.RequestUtils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;
import java.net.URLEncoder;

public class SubscribeRequestSender {

    private final String subscribeEndpoint = "http://92.63.107.176:8080/subscribe";
    private final String subscribersCountEndpoint = "http://92.63.107.176:8080/subscribe/subscribers_count/";
    private final String subscribesCountEndpoint = "http://92.63.107.176:8080/subscribe/subscribes_count/";

    /**
     Метод создает подписку по id пользователя и подписчика
     Если подписка уже существует, удаляет подписку
     /subscribe
     json body:  {
                "profileId": 0,
                "subscriberId": "string"
            }
     */
    //TODO создать подписку
    public Subscribe updateSubscribe(String body) throws IOException {
        //String encodedBody = URLEncoder.encode(body, "UTF-8");
        HttpPut httpPut = new HttpPut(subscribeEndpoint);
        RequestUtils.executePut(body, httpPut);
        return null;
    }


    /**
        Метод возвращает количество подписчиков по id пользователя
        /subscribers_count/{id}
    */
    //TODO получить количество подписчиков
    public Long getSubscribersCount(Long id) throws IOException {
        //String encodedBody = URLEncoder.encode(id, "UTF-8");
        HttpGet httpGet = new HttpGet(subscribersCountEndpoint + "/" + id);
        String subscribers = RequestUtils.executeGet(httpGet);
        return Long.parseLong(subscribers);
    }

    /**
        Метод возвращает количество подписок по id пользователя
        /subscribes_count/{id}
   */
    //TODO получить количество подписок
    public Long getSubscribesCount(Long id) throws IOException {
       // String encodedBody = URLEncoder.encode(id, "UTF-8");
        HttpGet httpGet = new HttpGet(subscribesCountEndpoint + "/" + id);
        String subscribes = RequestUtils.executeGet(httpGet);
        return Long.parseLong(subscribes);
    }

    /**
     Метод возвращает true если есть подписка, false если нет по id поста и id подписчика
     /subscribe/issubscribe/{userid}/{subscribeid}
     json body:  {
     "userId": "string",
     "subscribeId": "string"
     }
     */
    //TODO получить лайк
    public Boolean getSubscribeByUser(String userid, String subscribeId) throws IOException {
        String encodedUser = URLEncoder.encode(userid, "UTF-8");
        String encodedSubscribe = URLEncoder.encode(subscribeId, "UTF-8");
        HttpGet httpGet = new HttpGet(subscribeEndpoint + "/is_subscribed/" + encodedUser + "/" + encodedSubscribe);
        String subscribe = RequestUtils.executeGet(httpGet);
        if (subscribe.equals("true")){
            return true;
        }
        return false;
    }
}
