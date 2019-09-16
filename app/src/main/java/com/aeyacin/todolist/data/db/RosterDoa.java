package com.aeyacin.todolist.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aeyacin.todolist.data.db.entities.ToDoRoster;

import java.util.List;

@Dao
public interface RosterDoa {


    @Insert
    void add(ToDoRoster roster);

    @Query("SELECT * FROM todoroster WHERE  Id=:rosterId LIMIT 1")
    ToDoRoster getRosters(int rosterId);


    @Insert
    void AddRosters(ToDoRoster... toDoRosters);

    @Insert
    void AddRosters(List<ToDoRoster> toDoRosters);


    @Query("SELECT * FROM todoroster WHERE  UserId=:userID ")
    List<ToDoRoster> getAllToDoRosterByUser(int userID);


    @Update
    void update(ToDoRoster roster);

    @Delete
    void delete(ToDoRoster roster);
}
