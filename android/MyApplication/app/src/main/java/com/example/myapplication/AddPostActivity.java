package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.login.NewUserLoginFragment;
import com.yandex.metrica.YandexMetrica;

public class AddPostActivity extends AppCompatActivity {
    private EditText textPost;
    private Button addPostButton;
    private ImageButton exitButton;
    public static final String USER_ID = "userId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        long userId = getIntent().getLongExtra(USER_ID, -1);
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
                String eventParameters = "{\"userID\":\""+ Long.toString(userId) +"\"}";
                YandexMetrica.reportEvent("New post", eventParameters);
                Toast.makeText(AddPostActivity.this, "NEWPOST " + Long.toString(userId), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}