package com.aeyacin.todolist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.multidex.MultiDex;

import com.aeyacin.todolist.data.db.entities.User;

@SuppressLint("Registered")
public class ToDoApplication extends Application {


    public ToDoApplication() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static MutableLiveData<User> loginUser = new MutableLiveData<>();

    public static User getSession() {
        return loginUser.getValue();
    }

    public static void setSession(User login) {
        loginUser.setValue(login);
    }

}
