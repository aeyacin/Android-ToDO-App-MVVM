package com.aeyacin.todolist.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aeyacin.todolist.data.db.entities.User;

import java.util.List;

/**
 * Created by aeyacin on 2019-09-12.
 */
@Dao
public interface UserDoa {


    @Insert
    void upsert(User user);

    @Query("SELECT * FROM users WHERE user_id =:userId LIMIT 1")
    LiveData<User> getuser(int userId);

    @Insert
    void AddUser(User user);

    @Insert
    void AddUsers(User... users);

    @Insert
    void AddUsers(List<User> users);

    @Query("SELECT * FROM users")
    List<User> getUsers();

    @Query("SELECT * FROM users WHERE user_name LIKE  :userNick")
    User getUser(String userNick);

    @Query("SELECT * FROM users WHERE user_name =:username  and password=:pass LIMIT 1")
    LiveData<User> getUser(String username, String pass);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
