package com.example.myapplication.ui.post;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.requestSender.PostRequestSender;
import com.yandex.metrica.YandexMetrica;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AddPostActivity extends AppCompatActivity {
    private EditText textPost;
    private Button addPostButton;
    private ImageButton exitButton;
    public static final String USER_ID = "userId";
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        userId = getIntent().getLongExtra(USER_ID, -1);
        textPost = findViewById(R.id.edit_new_post);
        addPostButton = findViewById(R.id.button_add_new_post);
        exitButton = findViewById(R.id.exit_button_add_post);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((textPost.getText().toString().equals(""))){
                    Toast.makeText(AddPostActivity.this, "Пожалуйста, введите текст поста", Toast.LENGTH_LONG).show();
                }
                else{
                    NewPost newPost = new NewPost();
                    newPost.execute("{"+
                            "\"userId\":\"" + userId + "\"," +
                            "\"content\":\"" + textPost.getText().toString() + "\""
                            +"}");

                }

            }
        });
    }

    class NewPost extends AsyncTask<String, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... id) {
            try{
                PostRequestSender postRequestSender = new PostRequestSender();
                postRequestSender.createPost(id[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String eventParameters = "{\"userID\":\""+ Long.toString(userId) +"\"}";
            YandexMetrica.reportEvent("New post", eventParameters);
            Toast.makeText(AddPostActivity.this, "NEWPOST " + Long.toString(userId), Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(AddPostActivity.this, MainActivity.class);
            final MyApplication app = (MyApplication) getApplicationContext();
            app.setUpdateFlag(true);
            AddPostActivity.this.startActivity(mainIntent);
            AddPostActivity.this.finish();
        }
    }
}