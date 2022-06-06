package com.example.myapplication.ui.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;

import com.example.myapplication.entities.User;
import com.example.myapplication.requestSender.UserRequestSender;
import com.example.myapplication.ui.user_info.UserInfoFragment;
import com.yandex.metrica.YandexMetrica;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


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
                if ((login.getText().toString().equals("")) ||
                        password.getText().toString().equals("")||
                        name.getText().toString().equals("")||
                        surname.getText().toString().equals("")||
                        email.getText().toString().equals("")||
                        phone.getText().toString().equals("")){
                    Toast.makeText(NewUserLoginFragment.this, "Пожалуйста,заполните все поля", Toast.LENGTH_LONG).show();

                }else {
                    NewUser newUser = new NewUser();
                    newUser.execute(login.getText().toString(), password.getText().toString(), "{" +
                            "  \"login\": \"" + login.getText().toString() +"\"," +
                            "  \"password\": \"" + password.getText().toString() +"\"," +
                            "  \"firstName\": \"" + name.getText().toString() +"\"," +
                            "  \"lastName\": \"" + surname.getText().toString() +"\"," +
                            "  \"email\": \"" + email.getText().toString() +"\"," +
                            "  \"phone\": \"" + phone.getText().toString() +"\"" +
                            "}");

                }
            }
        });
    }
    class NewUser extends AsyncTask<String, Void, Void> {
        User user;

        @Override
        protected Void doInBackground(String... body) {
            try{
                UserRequestSender userRequestSender = new UserRequestSender();
                user = userRequestSender.getLoginUser(body[0], body[1]);
                if (user == null){
                    user = userRequestSender.createUser(body[2]);
                }else{
                    user = null;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (user == null){
                Toast.makeText(NewUserLoginFragment.this, "Ошибка регистрации. Пользователь уже зарегистрирован.", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(NewUserLoginFragment.this, MainActivity.class);
                //intent.putExtra(UserInfoFragment.USER_ID, user.getId());

                String eventParameters = "{\"userLogin\":\""+ login.getText() +"\"}";
                YandexMetrica.reportEvent("New user", eventParameters);
                final MyApplication app = (MyApplication) getApplicationContext();
                app.setCurrentUser(user);
                app.setRecreateFlag(true);
                NewUserLoginFragment.this.startActivity(intent);
                NewUserLoginFragment.this.finish();
            }
        }
    }
}