package com.aeyacin.todolist.data.db.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class ToDoTest {

    private String Name = "My First Todo";

    private ToDo toDo;

    public ToDoTest() {
        toDo = new ToDo();
        toDo.setName(Name);
    }

    @Test
    public void getName() {

        assertEquals(toDo.getName(), Name);
    }


    @Test
    public void setName() {
    }
}