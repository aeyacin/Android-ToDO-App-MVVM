package com.aeyacin.todolist.model;

import java.util.ArrayList;
import java.util.List;

public class FilterObject {


    public String Name; //search
    public boolean Done = false; //COMPLETE
    public boolean Todo = false; //Not COMPLETE or process, işlmede
    public boolean Expired = false; //Not COMPLETE anda Deadline older to Now date

    public List<Integer> rosterList = new ArrayList<>();


    public boolean isNotFilter() {
        return !Done && !Todo && !Expired && (Name == null || Name.trim().length() < 1) && rosterList.size() == 0;
    }


}
