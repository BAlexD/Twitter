package com.example.myapplication.ui.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.SplashScreen;
import com.example.myapplication.entities.User;
import com.example.myapplication.requestSender.UserRequestSender;
import com.example.myapplication.ui.user_info.UserInfoFragment;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.yandex.metrica.YandexMetrica;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private ImageButton exitButton;
    private SignInButton googleButton;
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 11112222;

    boolean google_flag = false;

    private User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.edit_user);
        password = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_register);
        exitButton = findViewById(R.id.exitButtonLogin);
        googleButton = findViewById(R.id.button_email_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signInGoogle();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, NewUserLoginFragment.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((login.getText().toString().equals("")) ||
                (password.getText().toString().equals(""))){
                    Toast.makeText(LoginActivity.this, "Пожалуйста,заполните все поля", Toast.LENGTH_LONG).show();
                }
                else{
                    GetUser getUser = new GetUser();
                    getUser.execute( login.getText().toString() , password.getText().toString());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            String email = account.getEmail();
            String name = account.getDisplayName();
            String password = account.getId();

            google_flag = true;

            GetUser getUser = new GetUser();
            getUser.execute(name , password, "{" +
                    "  \"login\": \"" + name +"\"," +
                            "  \"password\": \"" + password +"\"," +
                            "  \"firstName\": \"" + name +"\"," +
                            "  \"lastName\": \"" + "google_enter" +"\"," +
                            "  \"email\": \"" + email +"\"," +
                            "  \"phone\": \"" +"null" +"\"" +
                            "}");
            //updateUI(account);
        } catch (ApiException e) {
            Toast.makeText(LoginActivity.this, "Ошибка авторизации. Повторите попытку.", Toast.LENGTH_LONG).show();
        }
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    class GetUser extends AsyncTask<String, Void, User> {
        User user;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected User doInBackground(String... id) {
            try{
                UserRequestSender userRequestSender = new UserRequestSender();
                user = userRequestSender.getLoginUser(id[0], id[1]);
                if (google_flag){
                    if (user == null){
                        user = userRequestSender.createUser(id[2]);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            return user;
        }

        @Override
        protected void onPostExecute(User result) {
            super.onPostExecute(result);
            if(google_flag){
                google_flag = false;
            }
            if (user == null){
                Toast.makeText(LoginActivity.this, "Ошибка авторизации. Проверьте входные данные", Toast.LENGTH_LONG).show();
            }
            else{
                String eventParameters = "{\"userLogin\":\""+ login.getText() +"\"}";
                YandexMetrica.reportEvent("authorization", eventParameters);

                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                //mainIntent.putExtra(UserInfoFragment.USER_ID, user.getId());
                final MyApplication app = (MyApplication) getApplicationContext();
                app.setCurrentUser(user);
                app.setRecreateFlag(true);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
            currentUser = result;

        }


    }
}