package com.aeyacin.todolist.ui.home.todolist;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CheckRosterAdapter extends ArrayAdapter<ToDoRoster> {

    public CheckRosterAdapter(Context context, List<ToDoRoster> rosters) {
        super(context, 0, rosters);
    }

    @Override
    public View getView(int position, View view, @NotNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.check_todoroster_item, parent, false);
        }
        checkHolderView checkHolderView = (checkHolderView) view.getTag();
        if (checkHolderView == null) {
            checkHolderView = new checkHolderView();
            checkHolderView.name = view.findViewById(R.id.checkbox_roster_item);
            view.setTag(checkHolderView);
        }

        final ToDoRoster roster = getItem(position);
        assert roster != null;
        checkHolderView.name.setText(roster.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && roster.getColor() > 0) {
            checkHolderView.name.setButtonTintList(ColorStateList.valueOf(roster.getColor()));
        }
        return view;
    }

    private class checkHolderView {
        public CheckBox name;
    }
}