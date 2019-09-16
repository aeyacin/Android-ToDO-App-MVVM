package com.aeyacin.todolist.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.aeyacin.todolist.base.GenericResponse;
import com.aeyacin.todolist.data.db.AppDatabase;
import com.aeyacin.todolist.data.db.LoginDao;
import com.aeyacin.todolist.data.db.entities.LoginTable;
import com.aeyacin.todolist.data.db.entities.User;

import java.util.Date;
import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class AuthRepository {

    // private MyApi api;
    private AppDatabase db;

    private LiveData<List<LoginTable>> allLoginData;


    public AuthRepository(Application application) {

        db = AppDatabase.getDatabase(application);
        allLoginData = db.getLoginDao().getDetails();

    }

    //region Local Login

    public void deleteLogin() {
        db.getLoginDao().deleteAllData();
    }

    public LiveData<List<LoginTable>> getAllLogin() {
        return allLoginData;
    }

    public List<LoginTable> getAllLogins() {
        return db.getLoginDao().getLogins();
    }

    public void addLogin(LoginTable data) {

        new LoginInsertion(db.getLoginDao()).execute(data);
    }

    private static class LoginInsertion extends AsyncTask<LoginTable, Void, Void> {

        private LoginDao loginDao;

        private LoginInsertion(LoginDao loginDao) {

            this.loginDao = loginDao;

        }

        @Override
        protected Void doInBackground(LoginTable... data) {

            loginDao.deleteAllData();

            loginDao.insertDetails(data[0]);
            return null;

        }

    }

    //endregion

    //region Local User

    /**
     * Add Server User and Login
     *
     * @param email
     * @param password
     * @return
     */
    public GenericResponse<User> userLogin(String email, String password) {
        GenericResponse<User> response = new GenericResponse<>();

        User user = db.getUserDao().getUser(email);
        if (user != null && user.getUserId() > 0) {
            response.setValue(user);
        } else {
            response.setValue(null);
            response.setError("Not found user");

        }

        /*
        Server
         return apiRequest {
            api.userLogin(email, password);
        }
         */

        return response;

    }

    /**
     * Add User And Save Login
     *
     * @param name
     * @param email
     * @param password
     * @return
     */
    public GenericResponse<User> userSignup(String name, String email, String password) {
        GenericResponse<User> response = new GenericResponse<>();
        User user = db.getUserDao().getUser(email);

        if (user != null) {
            response.setError("Avaible username");
            return response;
        }

        user = new User();
        user.setCreateDate(new Date().getTime());
        user.setUserName(email);
        user.setPassword(password);
        user.setName(name);
        db.getUserDao().AddUser(user);
        user = db.getUserDao().getUser(user.getUserName());

        if (user != null && user.getUserId() > 0) {
            response.setValue(user);
        } else {
            response.setValue(null);
            response.setError("Not Saved User");
        }

        /*
        return apiRequest {
            api.userSignup(name, email, password);
        }
         */
        return response;
    }

    public void saveUser(User user) {
        db.getUserDao().upsert(user);
    }

    public LiveData<User> getUser(int id) {
        return db.getUserDao().getuser(id);
    }

    public LiveData<User> getUser(String username, String pass) {

        return db.getUserDao().getUser(username, pass);
    }

    //endregion


}
