package com.aeyacin.todolist.base;

import java.util.ArrayList;

public class ResponseModelBase extends ModelBase {


    private ArrayList<String> messages = new ArrayList<>();

    public void setSuccess(boolean success) {
    }

    public boolean isSuccess() {
        return this.messages.size() <= 0;


    }

    public ArrayList<String> getErrors() {
        return messages;
    }

    public void setErrors(ArrayList<String> errors) {
        this.messages = errors;
    }

    public void setError(String error) {
        this.messages.add(error);
    }


}
