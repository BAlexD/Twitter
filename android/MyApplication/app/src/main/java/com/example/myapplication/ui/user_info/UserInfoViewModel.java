package com.example.myapplication.ui.user_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.User;
import com.example.myapplication.network.TestData;
import com.example.myapplication.ui.post.PostAdapter;

public class UserInfoViewModel extends ViewModel {

    private final MutableLiveData<String> userNickText;
    private final MutableLiveData<String> userDescriptionText;
    private final MutableLiveData<String> userFollowingText;
    private final MutableLiveData<String> userFollowersText;
    private User user;

    public UserInfoViewModel() {
        userNickText = new MutableLiveData<>();
        userDescriptionText = new MutableLiveData<>();
        userFollowingText = new MutableLiveData<>();
        userFollowersText = new MutableLiveData<>();
        loadUserInfo();
    }


    public MutableLiveData<String> getUserNickText() {
        return userNickText;
    }

    public MutableLiveData<String> getUserDescriptionText() {
        return userDescriptionText;
    }


    public MutableLiveData<String> getUserFollowingText() {
        return userFollowingText;
    }

    public MutableLiveData<String> getUserFollowersText() {
        return userFollowersText;
    }

    private void loadUserInfo() {
        user = TestData.getUser();
        userNickText.setValue(user.getNick());
        userDescriptionText.setValue(user.getDescription());
        userFollowersText.setValue(String.valueOf(user.getFollowersCount()));
        userFollowingText.setValue(String.valueOf(user.getFollowingCount()));
    }

}