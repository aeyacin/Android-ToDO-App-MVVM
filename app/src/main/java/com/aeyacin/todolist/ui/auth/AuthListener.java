package com.aeyacin.todolist.ui.auth;

import com.aeyacin.todolist.data.db.entities.User;

public interface AuthListener {

    void onStarted();

    void onSuccess(User user);

    void onFailure(String message);


}
