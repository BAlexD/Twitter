package com.example.myapplication.requestSender;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.Subscribe;
import com.example.myapplication.entities.User;
import com.example.myapplication.requestUtils.RequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommentRequestSender {
    private final String commentsEndpoint = "http://92.63.107.176:8080/comments";
    private final String userEndpoint = "http://92.63.107.176:8080/user";
    private final String postEndpoint = "http://92.63.107.176:8080/post";


    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     Метод создает комментарий по id пользователя, id поста и тексту комментария
     /comments
     json body:  {
     "post_id": "string",
     "user_id": "string",
     "content": "string"
     }
     */
    //TODO создать подписку
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Comment createComment(String body) throws IOException {
        //String encodedBody = URLEncoder.encode(body, "UTF-8");
        HttpPost httpPost = new HttpPost(commentsEndpoint);
        String comment = RequestUtils.executePost(body, httpPost);
        ObjectNode objectNode = objectMapper.readValue(comment, ObjectNode.class);
        Comment comment_;
        try{
            UserRequestSender userRequestSender = new UserRequestSender();
            User user = userRequestSender.getUser(objectNode.get("userId").toString());
            PostRequestSender postRequestSender = new PostRequestSender();
            Post post = postRequestSender.getPost(objectNode.get("postId").toString());

            String content = objectNode.get("content").toString();
            comment_ = new Comment(Long.parseLong(objectNode.get("id").toString()), user,
                   content.substring(1, content.length()-1), post);
        }catch(Exception e){
            return null;
        }
        return comment_;
    }

    /**
     Метод возвращает список комментариев по id поста
     /comments/post/{id}
     */
    //TODO получить список комментариев к посту
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Collection<Comment> getComments(String id) throws IOException {
        String encodedBody = URLEncoder.encode(id, "UTF-8");
        HttpGet httpGet = new HttpGet(commentsEndpoint + "/post/" + encodedBody);
        String comments = RequestUtils.executeGet(httpGet);
        List<Comment> comments_= new ArrayList<>();
        List<String> commentsStrings = RequestUtils.splitJsonToList(comments);

        try {
            for (String comment : commentsStrings) {
                ObjectNode objectNode = objectMapper.readValue(comment, ObjectNode.class);

                String user_id = objectNode.get("userId").toString();
                UserRequestSender userRequestSender = new UserRequestSender();
                User user_ = userRequestSender.getUser(user_id);

                String post_id = objectNode.get("postId").toString();
                PostRequestSender postRequestSender = new PostRequestSender();
                Post post_ = postRequestSender.getPost(post_id);

                String content = objectNode.get("content").toString();

                comments_.add(new Comment(Long.parseLong(objectNode.get("id").toString()),
                        user_, content.substring(1, content.length()-1), post_));

            }
        }catch (Exception e){
            return null;
        }
        return comments_;

    }

    /**
     Метод возвращает количество комментариев по id поста
     /comments/count/post/{id}
     */
    //TODO получить количество комментариев
    public Long getCommentsCount(Long id) throws IOException {
        HttpGet httpGet = new HttpGet(commentsEndpoint + "/count/post/" + id);
        String comments = RequestUtils.executeGet(httpGet);
        return Long.parseLong(comments);
    }
}
