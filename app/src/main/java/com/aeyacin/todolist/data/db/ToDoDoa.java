package com.aeyacin.todolist.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aeyacin.todolist.data.db.entities.ToDo;

import java.util.List;

@Dao
public interface ToDoDoa {

    @Insert
    void add(ToDo todo);

    @Query("SELECT * FROM todos WHERE  RosterId=:rosterID and Id =:todoID LIMIT 1")
    ToDo getTodo(int todoID, int rosterID);


    @Insert
    void AddTodos(ToDo... todos);

    @Insert
    void AddTodos(List<ToDo> todos);


    @Query("SELECT * FROM todos WHERE  UserId=:userID ")
    List<ToDo> getAllTodoByUser(int userID);

    @Query("SELECT * FROM todos WHERE RosterId =:rosterID ")
    List<ToDo> getAllTodoByRoster(int rosterID);

    @Update
    void update(ToDo todo);

    @Delete
    void delete(ToDo todo);


}
