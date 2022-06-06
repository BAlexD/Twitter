package com.example.myapplication.ui.search;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.MyApplication;
import com.example.myapplication.databinding.FragmentUserSearchBinding;
import com.example.myapplication.entities.User;
import com.example.myapplication.requestSender.SubscribeRequestSender;
import com.example.myapplication.requestSender.UserRequestSender;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class UserSearchFragment extends Fragment {

    private FragmentUserSearchBinding binding;
    private User currentUser;
    private Button searchButton;
    private RecyclerView usersRecyclerView;
    private EditText login;
    private SwipeRefreshLayout swipeRefreshLayout;

    private UserSearchAdapter userSearchAdapter;
    private UserSearchNotSubscribedAdapter userSearchNotSubscribedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserSearchViewModel searchViewModel =
                new ViewModelProvider(this).get(UserSearchViewModel.class);

        binding = FragmentUserSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        searchButton = binding.searchButton;
        usersRecyclerView = binding.usersSearchRecyclerView;
        login = binding.searchEditText;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        final MyApplication app = (MyApplication) UserSearchFragment.this.getActivity().getApplicationContext();
        currentUser = app.getCurrentUser();

        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().equals("")){
                    Toast.makeText(UserSearchFragment.this.getActivity(), "Пожалуйста,заполните все поля", Toast.LENGTH_LONG).show();
                }else{
                    if(currentUser == null){
                        try {
                            usersRecyclerView.setAdapter(getUserSearchNotSubscribedAdapter());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            usersRecyclerView.setAdapter(getUserSearchAdapter());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                if(currentUser == null){
                    if(userSearchAdapter != null) {
                        if (userSearchAdapter.getItemCount() != 0) {
                            try {
                                usersRecyclerView.setAdapter(getUserSearchNotSubscribedAdapter());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else{
                    if(userSearchNotSubscribedAdapter != null) {
                        if (userSearchNotSubscribedAdapter.getItemCount() != 0) {
                            try {
                                usersRecyclerView.setAdapter(getUserSearchAdapter());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(currentUser == null){
            try {
                usersRecyclerView.setAdapter(getUserSearchNotSubscribedAdapter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                usersRecyclerView.setAdapter(getUserSearchAdapter());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UserSearchAdapter getUserSearchAdapter() throws IOException {
        UserSearchAdapter.OnSubscribeClickListener onSubscribeClickListener = new UserSearchAdapter.OnSubscribeClickListener() {
            @Override
            public void onSubscribeClick(User user) {
                Toast.makeText(UserSearchFragment.this.getActivity(), "searching...", Toast.LENGTH_SHORT).show();
                UpdateSubscribe updateSubscribe = new UpdateSubscribe();
                updateSubscribe.execute("{"+
                        "\"profileId\":" + currentUser.getId() + "," +
                        "\"subscriberId\":" + user.getId() +
                        "}");
            }
        };

        UserSearchAdapter.SetButtonText setButtonText = new UserSearchAdapter.SetButtonText() {
            @Override
            public boolean isSubscribe(User user) throws ExecutionException, InterruptedException {
                CheckSubscribe checkSubscribe = new CheckSubscribe();
                checkSubscribe.execute(Long.toString(currentUser.getId()), Long.toString(user.getId()));
                return checkSubscribe.get();
            }
        };
        userSearchAdapter = new UserSearchAdapter(onSubscribeClickListener, setButtonText);

        SearchUsers searchUsers = new SearchUsers();
        if (!login.getText().toString().equals(""))
            searchUsers.execute(login.getText().toString());
        return userSearchAdapter;
    }

    public UserSearchNotSubscribedAdapter getUserSearchNotSubscribedAdapter() throws IOException {
        userSearchNotSubscribedAdapter = new UserSearchNotSubscribedAdapter();
        SearchUsers searchUsers = new SearchUsers();
        if (!login.getText().toString().equals(""))
            searchUsers.execute(login.getText().toString());
        return userSearchNotSubscribedAdapter;
    }

    class UpdateSubscribe extends AsyncTask<String, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                SubscribeRequestSender subscribeRequestSender = new SubscribeRequestSender();
                subscribeRequestSender.updateSubscribe(body[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final MyApplication app = (MyApplication) UserSearchFragment.this.getActivity().getApplicationContext();
            app.setUpdateFlag(true);
        }
    }

    class SearchUsers extends AsyncTask<String, Void, Void> {
        Collection<User> users;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... body) {
            try{
                UserRequestSender userRequestSender = new UserRequestSender();
                users = userRequestSender.getUsers(body[0]);
                if (currentUser != null){
                    User del = null;
                    for(User user: users){
                        if (user.getId() == currentUser.getId()) del = user;
                    }
                    if (del != null){
                        users.remove(del);
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final TextView textView = binding.textDashboardUserSearch;
            if (users.size() == 0){

                textView.setText("Пользователи не найдены");
            }else{
                textView.setText("");
            }
            if (currentUser != null){
                userSearchAdapter.setItems(users);
            }else{
                userSearchNotSubscribedAdapter.setItems(users);
            }
        }
    }


    class CheckSubscribe extends AsyncTask<String, Void, Boolean> {
        Boolean result;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Boolean doInBackground(String... body) {
            try{
                SubscribeRequestSender subscribeRequestSender = new SubscribeRequestSender();
                result = subscribeRequestSender.getSubscribeByUser(body[0], body[1]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return result;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    }
}