package com.example.myapplication.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SplashScreen;
import com.yandex.metrica.YandexMetrica;

public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private Button loginByEmailButton;
    private ImageButton exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.edit_user);
        password = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        loginByEmailButton = findViewById(R.id.button_email_login);
        registerButton = findViewById(R.id.button_register);
        exitButton = findViewById(R.id.exitButtonLogin);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, NewUserLoginFragment.class);
                LoginActivity.this.startActivity(mainIntent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventParameters = "{\"userLogin\":\""+ login.getText() +"\"}";
                YandexMetrica.reportEvent("authorization", eventParameters);
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
        });
    }
}