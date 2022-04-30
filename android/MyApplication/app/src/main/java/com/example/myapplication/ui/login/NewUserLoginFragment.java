package com.example.myapplication.ui.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import com.yandex.metrica.YandexMetrica;


public class NewUserLoginFragment extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private Button registerButton;
    private ImageButton exitButtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_new_user_login);

        login = findViewById(R.id.edit_user_new);
        password = findViewById(R.id.edit_password_new);
        name = findViewById(R.id.edit_name_new);
        surname = findViewById(R.id.edit_surname_new);
        email = findViewById(R.id.edit_email_new);
        phone = findViewById(R.id.edit_phone_new);
        registerButton = findViewById(R.id.button_new_login);

        exitButtton =findViewById(R.id.exitButtonRegister);

        exitButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewUserLoginFragment.this, "new user " + login.getText(), Toast.LENGTH_SHORT).show();
                String eventParameters = "{\"userLogin\":\""+ login.getText() +"\"}";
                YandexMetrica.reportEvent("New user", eventParameters);
            }
        });
    }
}