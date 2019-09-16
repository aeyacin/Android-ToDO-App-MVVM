package com.aeyacin.todolist.ui.addtodo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aeyacin.todolist.ToDoApplication;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;
import com.aeyacin.todolist.data.repositories.ToDoRepository;
import com.aeyacin.todolist.model.ProcessType;
import com.aeyacin.todolist.model.ToDoStatus;
import com.aeyacin.todolist.ui.base.ActivityListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddToDoViewModel extends AndroidViewModel {

    private ToDo toDoItem;

    public MutableLiveData<Integer> TodoID = new MutableLiveData<>();
    public MutableLiveData<String> TodoTitle = new MutableLiveData<>();
    public MutableLiveData<Integer> RosterID = new MutableLiveData<>();
    public MutableLiveData<String> TodoDescription = new MutableLiveData<>();
    public MutableLiveData<String> TodoDate = new MutableLiveData<>();
    public MutableLiveData<String> TodoTime = new MutableLiveData<>();
    public MutableLiveData<Long> DeadLine = new MutableLiveData<>();

    public MutableLiveData<Integer> IsActiveDelete = new MutableLiveData<>();

    private ToDoRepository toDoRepository;


    private ActivityListener activityListener;


    private Calendar calendar;

    private int year, month, day, hour, minute, second;

    public AddToDoViewModel(@NonNull Application application) {
        super(application);
        toDoRepository = new ToDoRepository(application);
        IsActiveDelete.setValue(View.INVISIBLE);
        calendar = Calendar.getInstance();

        setDate();
    }


    public ActivityListener getActivityListener() {
        return activityListener;
    }

    public void setActivityListener(ActivityListener activityListener) {
        this.activityListener = activityListener;


    }

    public void Edit(ToDo toDo) {
        if (toDo != null && toDo.getId() > 0) {
            toDoItem = toDo;
            TodoID.setValue(toDo.getId());
            TodoTitle.setValue(toDo.getName());
            TodoDescription.setValue(toDo.getDescription());
            RosterID.setValue(toDo.getRosterId());
            if (toDo.getDeadLine() != null && toDo.getDeadLine() > 0)
                calendar.setTimeInMillis(toDo.getDeadLine());

            // TodoDate.setValue(toDo.getDeadLine());
        }
        if (RosterID.getValue() == null) {
            IsActiveDelete.setValue(View.INVISIBLE);
        } else {
            IsActiveDelete.setValue(View.VISIBLE);

        }
        setDate();

    }

    @SuppressLint("DefaultLocale")
    private void setDate() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        try {

            @SuppressLint("DefaultLocale") String d = (String.format("%02d", day) + "/" + String.format("%02d", month + 1) + "/" + year);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date MyDate = newDateFormat.parse(d);
            // newDateFormat.applyPattern("EE d MMM yyyy");
            String MySDate = newDateFormat.format(MyDate);
            TodoDate.setValue(MySDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TodoTime.setValue(String.format("%02d", hour) + ":" + String.format("%02d", minute));


    }

    @SuppressLint("DefaultLocale")
    public void SetDate(int year, int month, int day) {
        if (year > 0)
            this.year = year;
        if (month > 0)
            this.month = month;
        if (day > 0)
            this.day = day;

        calendar.set(this.year, this.month, this.day);


    }

    @SuppressLint("DefaultLocale")
    public void SetTime(int hour, int minute, int second) {
        if (hour >= 0)
            this.hour = hour;
        if (minute >= 0)
            this.minute = minute;
        if (year >= 0)
            this.second = second;
        calendar.set(this.year, this.month, this.day, this.day, this.minute);


    }

    public List<ToDoRoster> getRosterList() {
        return toDoRepository.getTodoRosterList();
    }

    public void onDelete(View v) {

        if (TodoID.getValue() != null && TodoID.getValue() > 0) {
            toDoRepository.deleteTodo(toDoItem);
            activityListener.onSuccess(true);
        }

    }

    public void onClose(View v) {
        activityListener.onSuccess(false);

    }

    public void onSave(View v) {

        Date date = calendar.getTime();

        if (TodoID.getValue() != null && TodoID.getValue() > 0) {
            ToDo toDo = toDoItem;
            toDo.setDescription(TodoDescription.getValue());
            toDo.setName(TodoTitle.getValue());
            toDo.setUserId(ToDoApplication.getSession().getUserId());
            toDo.setRosterId(RosterID.getValue());
            toDo.setDeadLine(date.getTime());
            toDo.setCreateDate(new Date().getTime());
            toDoRepository.updateTodo(toDo);


        } else {
            ToDo toDo = new ToDo();
            toDo.setName(TodoTitle.getValue());
            toDo.setDescription(TodoDescription.getValue());
            toDo.setUserId(ToDoApplication.getSession().getUserId());
            toDo.setRosterId(RosterID.getValue());
            toDo.setDeadLine(date.getTime());
            toDo.setCreateDate(new Date().getTime());
            toDo.setIdentifier(UUID.randomUUID().toString());
            toDo.setStatus(ToDoStatus.TODO.toString());
            toDoRepository.addTodo(toDo);
            activityListener.onProcess(ProcessType.ADD, toDo);

        }

        activityListener.onSuccess(true);

    }

    private void setNotification(ToDo item) {
    }

    public Calendar getCalendar() {
        return calendar;
    }


}
