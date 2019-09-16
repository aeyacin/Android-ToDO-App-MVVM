package com.aeyacin.todolist.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aeyacin.todolist.data.db.entities.LoginTable;

import java.util.List;

/**
 *
 */
@Dao
public interface LoginDao {

    @Insert
    void insertDetails(LoginTable data);

    @Query("select * from LoginDetails")
    LiveData<List<LoginTable>> getDetails();

    @Query("select * from LoginDetails")
    List<LoginTable> getLogins();

    @Query("delete from LoginDetails")
    void deleteAllData();

}
