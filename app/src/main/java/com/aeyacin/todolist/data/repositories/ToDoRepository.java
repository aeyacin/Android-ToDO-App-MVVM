package com.aeyacin.todolist.data.repositories;

import android.app.Application;

import com.aeyacin.todolist.ToDoApplication;
import com.aeyacin.todolist.data.db.AppDatabase;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ToDoRepository {

    private AppDatabase db;


    public ToDoRepository(Application application) {

        db = AppDatabase.getDatabase(application);

    }


    public void addTodo(ToDo toDo) {
        db.getToDoDao().add(toDo);
    }

    public void addTodoRoster(ToDoRoster toDoRoster) {
        db.getRosterDao().add(toDoRoster);
    }

    public void deleteTodoRoster(ToDoRoster toDoRoster) {
        db.getRosterDao().delete(toDoRoster);
    }

    public void deleteTodo(ToDo toDo) {
        db.getToDoDao().delete(toDo);
    }

    public void deleteTodoReminder(String identifierKey) {

        for (ToDo item : getTodoList()) {
            if (item.getIdentifier().equals(identifierKey)) {
                db.getToDoDao().delete(item);
            }
        }

    }

    public void updateTodo(ToDo toDo) {
        db.getToDoDao().update(toDo);
    }

    public void updateTodoRoster(ToDoRoster toDoRoster) {
        db.getRosterDao().update(toDoRoster);
    }

    public List<ToDo> getTodoList() {
        return db.getToDoDao().getAllTodoByUser(ToDoApplication.getSession().getUserId());
    }

    public List<ToDo> getTodoListByRoster(int rosterId) {
        return db.getToDoDao().getAllTodoByRoster(rosterId);
    }

    public List<ToDo> getTodoListByRosters(List<Integer> rosterIds) {
        List<ToDo> toDoListResult = new ArrayList<>();
        List<ToDo> toDoList = getTodoList();
        for (ToDo item : toDoList) {
            if (rosterIds.contains(item.getRosterId())) {
                toDoListResult.add(item);
            }
        }


        return toDoListResult;
    }

    public List<ToDoRoster> getTodoRosterList() {
        List<ToDoRoster> result = db.getRosterDao().getAllToDoRosterByUser(ToDoApplication.getSession().getUserId());
        if (result != null && result.size() > 0)
            return result;
        ToDoRoster toDoRoster = new ToDoRoster();
        toDoRoster.setName("None");
        toDoRoster.setShow(true);
        toDoRoster.setUserId(ToDoApplication.getSession().getUserId());
        db.getRosterDao().add(toDoRoster);

        return db.getRosterDao().getAllToDoRosterByUser(ToDoApplication.getSession().getUserId());
    }

    public void setAutoRoster() {

        List<ToDoRoster> rosterList = getTodoRosterList();
        ToDoRoster noneItem = null;
        for (ToDoRoster item : rosterList) {
            if (item.getName().equalsIgnoreCase("None")) {
                noneItem = item;
                break;
            }
        }

        List<ToDo> list = getTodoList();
        for (ToDo item : list) {
            for (ToDoRoster roster : rosterList) {
                if (item.getRosterId() == roster.getId())
                    break;
            }
            if (noneItem != null) {
                item.setRosterId(noneItem.getId());
                updateTodo(item);
            }

        }


    }
}
