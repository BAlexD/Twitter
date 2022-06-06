package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.entities.User;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class MyApplication extends Application {
    private boolean recreateFlag = false;
    private boolean updateFlag = false;
    private boolean updateWallFlag = false;
    private User currentUser = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("5063bf77-f792-400c-9651-ba4d38e68ca3").build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isRecreateFlag() {
        return recreateFlag;
    }

    public void setRecreateFlag(boolean recreate_flag) {
        this.recreateFlag = recreate_flag;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public boolean isUpdateWallFlag() {
        return updateWallFlag;
    }

    public void setUpdateWallFlag(boolean updateWallFlag) {
        this.updateWallFlag = updateWallFlag;
    }
}