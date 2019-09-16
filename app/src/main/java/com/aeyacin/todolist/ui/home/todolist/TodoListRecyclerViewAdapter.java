package com.aeyacin.todolist.ui.home.todolist;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.model.ToDoStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ToDo} and makes a call to the
 * specified {@link TodoListFragment.OnToDoListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListRecyclerViewAdapter.ViewHolder> {

    private List<ToDo> mValues = new ArrayList<>();

    private final TodoListFragment.OnToDoListFragmentInteractionListener mListener;

    public TodoListRecyclerViewAdapter(List<ToDo> items, TodoListFragment.OnToDoListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_todo_list_item, parent, false);
        return new ViewHolder(view);
    }

    public void RefreshData(List<ToDo> items) {
        mValues = items;

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.back.setBackgroundColor(Color.WHITE);
        holder.title.setText(holder.mItem.getName());
        int color = holder.mItem.getColor() > 0 ? holder.mItem.getColor() : Color.DKGRAY;// holder.mItem.getColor();// Color.parseColor(holder.mItem.getColor());
        holder.categoriColor.setBackgroundColor(Color.WHITE);

        holder.text.setText(holder.mItem.getDescription() == null ? "" : holder.mItem.getDescription());
        Calendar date = Calendar.getInstance();
        if (holder.mItem.getCreateDate() != null)
            date.setTime(new Date(holder.mItem.getDeadLine()));
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1; // Note: zero based!
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        holder.dateMonth.setText(String.format("%02d", month + 1));
        holder.dateYear.setText(year + "");
        holder.dateHour.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));

        holder.categoriColor.setBackgroundColor(color);


        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onToDoListFragmentInteraction(holder.mItem, ToDoStatus.EDIT);
                }
            }
        });
        holder.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onToDoListFragmentInteraction(holder.mItem, ToDoStatus.COMPLETE);
                }
            }
        });
        holder.todoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onToDoListFragmentInteraction(holder.mItem, ToDoStatus.TODO);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ToDo mItem;
        public TextView title;
        public TextView text;
        public TextView dateHour;
        public TextView dateYear;
        public TextView dateMonth;
        public TextView categoriColor;
        public LinearLayout back;
        public ImageView editBtn;
        public ImageView doneBtn;
        public ImageView todoBtn;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            text = (TextView) view.findViewById(R.id.text);
            dateHour = (TextView) view.findViewById(R.id.dateHour);
            dateMonth = (TextView) view.findViewById(R.id.dateMonth);
            dateYear = (TextView) view.findViewById(R.id.dateYear);
            categoriColor = (TextView) view.findViewById(R.id.catregory_color);
            back = (LinearLayout) view.findViewById(R.id.back);

            editBtn = view.findViewById(R.id.edit_item);
            doneBtn = view.findViewById(R.id.done_item);
            todoBtn = view.findViewById(R.id.todo_item);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}
