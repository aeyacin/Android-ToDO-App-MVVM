package com.aeyacin.todolist.ui.base;


import com.aeyacin.todolist.model.ProcessType;

public interface ActivityListener {
    void onStarted();

    void onSuccess(Boolean state);

    void onFailure(String message);

    void onProcess(ProcessType processType, Object data);
}
