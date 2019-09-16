package com.aeyacin.todolist.base;


import com.google.gson.annotations.SerializedName;

/**
 * GenericResponse
 * @param <T>
 */
public class GenericResponse<T> extends ResponseModelBase {


    @SerializedName("Value")
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {

        this.value = value;
    }

}
