package com.aeyacin.todolist.ui.home.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;
import com.aeyacin.todolist.data.repositories.AuthRepository;
import com.aeyacin.todolist.data.repositories.ToDoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TodoViewModel extends AndroidViewModel {


    private final ToDoRepository repository;


    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoRepository(application);

    }

    public List<ToDo> getTodoItems() {
        return repository.getTodoList();
    }


}
