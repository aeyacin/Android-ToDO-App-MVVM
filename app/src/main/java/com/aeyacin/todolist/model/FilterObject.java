package com.aeyacin.todolist.model;

import java.util.ArrayList;
import java.util.List;

public class FilterObject {


    public String Name = null; //search
    public boolean Done = true; //COMPLETE
    public boolean Todo = true; //Not COMPLETE or process, işlmede
    public boolean Expired = true; //Not COMPLETE anda Deadline older to Now date

    public List<Integer> rosterList = new ArrayList<>();
    public int rosterCount = 0;

    public boolean isNotFilter() {
        return !Done && !Todo && !Expired && (Name == null || Name.trim().length() < 1) && (rosterList.size() == 0 || rosterCount == rosterList.size());
    }


}
