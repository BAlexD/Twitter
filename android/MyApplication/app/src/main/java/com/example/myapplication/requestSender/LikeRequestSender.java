package com.example.myapplication.requestSender;

import com.example.myapplication.entities.Like;
import com.example.myapplication.entities.Subscribe;
import com.example.myapplication.requestUtils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;
import java.net.URLEncoder;

public class LikeRequestSender {
    private final String likesEndpoint = "http://92.63.107.176:8080/likes";
    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     Метод создает лайк по id пользователя и поста
     Если лайк уже существует, удаляет лайк
     /likes
     json body:  {
     "postId": 0,
     "userId": 0
     }
     */
    //TODO создать лайк
    public Like updateLike(String postid, String userid) throws IOException {
        String encodedUser = URLEncoder.encode(userid, "UTF-8");
        String encodedPost = URLEncoder.encode(postid, "UTF-8");
        HttpPut httpPut = new HttpPut(likesEndpoint + "?postId=" +encodedPost+ "&userId="+encodedUser);
        String body = "{\"postId\":" + postid+", \"userId\":" + userid + "}";
        RequestUtils.executePut(body, httpPut);
        return null;
    }

    /**
     Метод возвращает количество лайков по id поста
     /likes/count/{id}
     */
    //TODO получить количество лайков
    public Long getLikesCount(String id) throws IOException {
        String encodedId = URLEncoder.encode(id, "UTF-8");
        HttpGet httpGet = new HttpGet(likesEndpoint + "/count/" + encodedId);
        String likes = RequestUtils.executeGet(httpGet);
        return Long.parseLong(likes);
    }

    /**
     Метод возвращает true усли пост лайкнут, false если не лайкнут по id поста и id пользователя
     /likes/isliked/{postId}/{userId}
     json body:  {
     "postId": "string",
     "userId": "string"
     }
     */
    //TODO получить лайк
    public Boolean getLikesBuUser(String postid, String userid) throws IOException {
        String encodedUser = URLEncoder.encode(userid, "UTF-8");
        String encodedPost = URLEncoder.encode(postid, "UTF-8");
        HttpGet httpGet = new HttpGet(likesEndpoint + "/isliked/" + encodedPost + "/" + encodedUser);
        String like = RequestUtils.executeGet(httpGet);
        if (like.equals("true")){
            return true;
        }
        //ObjectNode objectNodeLike = objectMapper.readValue(like, ObjectNode.class);
        return false;
    }
}
