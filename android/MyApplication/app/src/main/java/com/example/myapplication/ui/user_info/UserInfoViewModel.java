package com.example.myapplication.ui.user_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.User;
import com.example.myapplication.network.TestData;
import com.example.myapplication.ui.post.PostAdapter;

public class UserInfoViewModel extends ViewModel {

    //private final MutableLiveData<String> userImageView;
    private final MutableLiveData<String> userNameText;
    private final MutableLiveData<String> userNickText;
    private final MutableLiveData<String> userDescriptionText;
    private final MutableLiveData<String> userLocationText;
    private final MutableLiveData<String> userFollowingText;
    private final MutableLiveData<String> userFollowersText;
    private final MutableLiveData<PostAdapter> postAdapterData;
    private User user;

    public UserInfoViewModel() {
        userNameText = new MutableLiveData<>();
        userNickText = new MutableLiveData<>();
        userDescriptionText = new MutableLiveData<>();
        userLocationText = new MutableLiveData<>();
        userFollowingText = new MutableLiveData<>();
        userFollowersText = new MutableLiveData<>();
        postAdapterData = new MutableLiveData<>();
        loadUserInfo();
    }

    public LiveData<String> getUserNameText() {
        return userNameText;
    }

    public MutableLiveData<String> getUserNickText() {
        return userNickText;
    }

    public MutableLiveData<String> getUserDescriptionText() {
        return userDescriptionText;
    }

    public MutableLiveData<String> getUserLocationText() {
        return userLocationText;
    }

    public MutableLiveData<String> getUserFollowingText() {
        return userFollowingText;
    }

    public MutableLiveData<String> getUserFollowersText() {
        return userFollowersText;
    }

    private void loadUserInfo() {
        user = TestData.getUser();
        userNameText.setValue(user.getName());
        userNickText.setValue(user.getNick());
        userDescriptionText.setValue(user.getDescription());
        userLocationText.setValue(user.getLocation());
        userFollowersText.setValue(String.valueOf(user.getFollowersCount()));
        userFollowingText.setValue(String.valueOf(user.getFollowingCount()));
    }

    private void displayUserInfo(User user) {
        //Picasso.with(this).load(user.getImageUrl()).into(userImageView);

    }


}