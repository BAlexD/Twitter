package com.example.myapplication.ui.post;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.network.TestData;
import com.example.myapplication.ui.user_info.UserInfoFragment;
import com.yandex.metrica.YandexMetrica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class CommentListFragment  extends AppCompatActivity{
    private static final String TWITTER_RESPONSE_FORMAT="EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // Thu Oct 26 07:31:08 +0000 2017
    private static final String MONTH_DAY_FORMAT = "MMM d"; // Oct 26

    public static final String POST_ID = "postId";

    private TextView nameTextView;
    private TextView nickTextView;
    private TextView creationDateTextView;
    private TextView contentTextView;
    private ImageButton exitButtton;
    private CommentAdapter commentAdapter;
    private Collection<Comment> comments;
    private EditText commentText;
    private Button commentButton;

    private RecyclerView commentsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comment_list);
        long postId = getIntent().getLongExtra(POST_ID, -1);
        Toast.makeText(this, "UserId = " + postId, Toast.LENGTH_SHORT).show();

        nameTextView = findViewById(R.id.comment_author_name_text_view);
        nickTextView = findViewById(R.id.comment_author_nick_text_view);
        creationDateTextView = findViewById(R.id.comment_creation_date_text_view);
        contentTextView = findViewById(R.id.comment_tweet_content_text_view);
        commentText = findViewById(R.id.comment_edit_text);
        commentButton = findViewById(R.id.add_comment_button);
        exitButtton =findViewById(R.id.exitButton);
        exitButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentText.getText() != null){
                    Toast.makeText(CommentListFragment.this, "comment " + postId+":"+commentText.getText(), Toast.LENGTH_SHORT).show();
                    String eventParameters = "{\"postID\":\""+postId+"\"}";
                    YandexMetrica.reportEvent("New comment", eventParameters);
                }
            }
        });

        initRecyclerView();
        loadPostInfo(postId);
        loadComments(postId);
    }

    private void initRecyclerView() {
        commentsRecyclerView = findViewById(R.id.comment_list);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter();
        commentsRecyclerView.setAdapter(commentAdapter);
    }

    private void loadPostInfo(final Long post_id){
        nameTextView.setText(TestData.getUser().getName());
        nickTextView.setText(TestData.getUser().getNick());
        contentTextView.setText("content");
        String creationDateFormatted = getFormattedDate("Thu Dec 13 07:31:08 +0000 2017");
        creationDateTextView.setText(creationDateFormatted);
    }
    private void loadComments(final Long post_id){
        comments = TestData.getComments();
        commentAdapter.setItems(comments);
    }


    private String getFormattedDate(String rawDate) {
        SimpleDateFormat utcFormat = new SimpleDateFormat(TWITTER_RESPONSE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat displayedFormat = new SimpleDateFormat(MONTH_DAY_FORMAT, Locale.getDefault());
        try {
            Date date = utcFormat.parse(rawDate);
            return displayedFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}