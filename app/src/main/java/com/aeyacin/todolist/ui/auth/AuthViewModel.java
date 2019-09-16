package com.aeyacin.todolist.ui.auth;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.ToDoApplication;
import com.aeyacin.todolist.base.GenericResponse;
import com.aeyacin.todolist.data.db.entities.LoginTable;
import com.aeyacin.todolist.data.db.entities.User;
import com.aeyacin.todolist.data.repositories.AuthRepository;
import com.aeyacin.todolist.ui.home.HomeActivity;

import java.util.List;
import java.util.Objects;

public class AuthViewModel extends AndroidViewModel {

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> passwordconfirm = new MutableLiveData<>();

    private LiveData<List<LoginTable>> getAllData;


    private final AuthRepository repository;


    private AuthListener authListener;


    public AuthViewModel(Application application) {
        super(application);
        repository = new AuthRepository(application);
        getAllData = repository.getAllLogin();


    }

    private void login(LoginTable data) {
        repository.addLogin(data);
    }

    private void logout() {
        repository.deleteLogin();
    }

    public LiveData<List<LoginTable>> getGetAllLoginData() {
        return getAllData;

    }

    public LiveData<List<LoginTable>> getLoggedInUser() {
        return getAllData;

    }


    public void onLoginButtonClick(View view) {
        if (this.authListener == null)
            return;

        authListener.onStarted();
        if (email.getValue() == null || password.getValue() == null) {
            authListener.onFailure(view.getContext().getString(R.string.invalid_email_or_pass));
            return;
        }
        GenericResponse<User> response = repository.userLogin(email.getValue(), password.getValue());
        if (response.isSuccess() && response.getValue() != null) {
            //Add Login to local
            LoginTable loginTable = new LoginTable();
            loginTable.setEmail(response.getValue().getUserName());
            loginTable.setPassword(response.getValue().getPassword());
            loginTable.setLoginId(response.getValue().getUserId());
            login(loginTable);
            authListener.onSuccess(response.getValue());
            ToDoApplication.setSession(response.getValue());
            onHome(view);
        } else {
            authListener.onFailure(response.getErrors().get(0));
        }

     /*     Coroutines.main {
            try {
                AuthResponse authResponse = repository.userLogin(email , password );
                authResponse.user.let {
                    authListener.onSuccess(it)
                    repository.saveUser(it)
                    return @main
                }
                authListener.onFailure(authResponse.message !!)
            } catch (ApiException e){
                authListener.onFailure(e.message !!)
            }catch(NoInternetException e){
                authListener.onFailure(e.message !!)
            }
        }
        */
    }

    public void onLogin(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
        try {
            ((Activity) view.getContext()).finish();
        } catch (Exception ignored) {

        }
    }

    public void onLogout(View view) {
        // logout();
        view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
        try {
            ((Activity) view.getContext()).finish();
        } catch (Exception ignored) {

        }

    }

    public void onSignup(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), SignupActivity.class));
        try {
            ((Activity) view.getContext()).finish();
        } catch (Exception ignored) {

        }
    }

    public void onHome(View view) {

        view.getContext().startActivity(new Intent(view.getContext(), HomeActivity.class));
        try {
            ((Activity) view.getContext()).finish();
        } catch (Exception ignored) {

        }
    }

    public void onSignupButtonClick(View view) {
        if (this.authListener == null)
            return;
        this.authListener.onStarted();

        if (Objects.requireNonNull(name.getValue()).isEmpty()) {
            this.authListener.onFailure(view.getContext().getString(R.string.name_is_required));
            return;
        }

        if (Objects.requireNonNull(email.getValue()).isEmpty()) {
            authListener.onFailure(view.getContext().getString(R.string.mail_is_required));
            return;
        }


        if (Objects.requireNonNull(password.getValue()).isEmpty()) {
            authListener.onFailure(view.getContext().getString(R.string.please_enter_pass));
            return;
        }

        if (!password.getValue().equals(passwordconfirm.getValue())) {
            this.authListener.onFailure(view.getContext().getString(R.string.password_not_match));
            return;
        }

        GenericResponse<User> response = repository.userSignup(name.getValue(), email.getValue(), password.getValue());

        if (response.isSuccess() && response.getValue() != null) {
            this.authListener.onSuccess(response.getValue());
            onLogin(view);
        } else {
            this.authListener.onFailure(response.getErrors().get(0));

        }

/*
        Coroutines.main {
            try {
                val authResponse = repository.userSignup(name !!, email !!, password !!)
                authResponse.user ?.let {
                    authListener ?.onSuccess(it)
                    repository.saveUser(it)
                    return @main
                }
                authListener ?.onFailure(authResponse.message !!)
            } catch (e:ApiException){
                authListener ?.onFailure(e.message !!)
            }catch(e:NoInternetException){
                authListener ?.onFailure(e.message !!)
            }
        }
*/
    }


    public void setAuthListener(AuthListener authListener) {
        this.authListener = (authListener);
    }





}
