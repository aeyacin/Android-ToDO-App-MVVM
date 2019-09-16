package com.aeyacin.todolist.ui.auth;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class AuthViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;


    public AuthViewModelFactory(Application application) {
        this.application = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}
