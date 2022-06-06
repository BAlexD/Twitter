package com.example.myapplication.ui.post;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.network.TestData;
import com.example.myapplication.requestSender.CommentRequestSender;
import com.example.myapplication.requestSender.PostRequestSender;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.user_info.UserInfoFragment;
import com.yandex.metrica.YandexMetrica;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CommentListFragment  extends AppCompatActivity{

    public static final String POST_ID = "postId";

    private TextView nickTextView;
    private TextView contentTextView;
    private ImageButton exitButtton;
    private CommentAdapter commentAdapter;
    private Collection<Comment> comments;
    private EditText commentText;
    private Button commentButton;

    private Post currentPost = null;
    private long userId;
    private RecyclerView commentsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comment_list);

        long postId = getIntent().getLongExtra(POST_ID, -1);
        GetPost getPost = new GetPost();
        getPost.execute(Long.toString(postId));

        try {
            currentPost = getPost.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //Toast.makeText(this, "PostId = " + postId, Toast.LENGTH_SHORT).show();
        final MyApplication app = (MyApplication) CommentListFragment.this.getApplicationContext();
        userId = app.getCurrentUser().getId();

        nickTextView = findViewById(R.id.comment_author_nick_text_view);
        contentTextView = findViewById(R.id.comment_tweet_content_text_view);
        commentText = findViewById(R.id.comment_edit_text);
        commentButton = findViewById(R.id.add_comment_button);
        exitButtton =findViewById(R.id.exitButton);

        commentsRecyclerView = findViewById(R.id.comment_list);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        nickTextView.setText(currentPost.getUser().getNick());
        contentTextView.setText(currentPost.getText());


        exitButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setUpdateFlag(true);
                onBackPressed();
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentText.getText().toString().equals("")){
                    Toast.makeText(CommentListFragment.this, "Пожалуйста,заполните все поля", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CommentListFragment.this, "comment " + postId+":"+commentText.getText(), Toast.LENGTH_SHORT).show();
                    String eventParameters = "{\"postID\":\""+postId+"\"}";
                    YandexMetrica.reportEvent("New comment", eventParameters);
                    PostComment postComment = new PostComment();
                    postComment.execute("{" +
                            "\"postId\":" + postId + "," +
                            "\"userId\":" + userId +"," +
                            "\"content\":\"" +  commentText.getText().toString() + "\"}");

                }
            }
        });

        commentAdapter = new CommentAdapter();
        try {
            commentsRecyclerView.setAdapter(getCommentAdapter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommentAdapter getCommentAdapter() throws IOException {
        commentAdapter = new CommentAdapter();
        GetComments getComments = new GetComments();
        getComments.execute(Long.toString(currentPost.getId()));
        return commentAdapter;
    }

    class GetPost extends AsyncTask<String, Void, Post> {
       Post post;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Post doInBackground(String... body) {
            try{
                PostRequestSender postRequestSender = new PostRequestSender();
                post = postRequestSender.getPost(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return post;
        }

        @Override
        protected void onPostExecute(Post post) {
            super.onPostExecute(post);
            currentPost = post;
        }
    }

    class PostComment extends AsyncTask<String, Void, Void> {
        Comment comment;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                CommentRequestSender commentRequestSender = new CommentRequestSender();
                comment = commentRequestSender.createComment(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //commentAdapter.add()
            commentAdapter.setItem(comment);
            final TextView textView = findViewById(R.id.text_dashboard_comment_list);
            textView.setText("");
            final MyApplication app = (MyApplication) CommentListFragment.this.getApplicationContext();
            app.setUpdateFlag(true);
            app.setUpdateWallFlag(true);
        }
    }

    class GetComments extends AsyncTask<String, Void, Void> {
        Collection<Comment> comments;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                CommentRequestSender commentRequestSender = new CommentRequestSender();
                comments = commentRequestSender.getComments(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final TextView textView = findViewById(R.id.text_dashboard_comment_list);
            if (comments.size() == 0){

                textView.setText("Добавьте первый комментарий!");
            }else{
                textView.setText("");
            }
            commentAdapter.setItems(comments);
        }
    }
}