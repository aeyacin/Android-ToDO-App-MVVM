package com.aeyacin.todolist.ui.home.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;
import com.aeyacin.todolist.model.FilterObject;
import com.aeyacin.todolist.model.OrderStatus;
import com.aeyacin.todolist.model.ToDoStatus;
import com.aeyacin.todolist.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnToDoListFragmentInteractionListener}
 * interface.
 */
public class TodoListFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnToDoListFragmentInteractionListener mListener;

    private TodoViewModel todoViewModel;

    private TodoListRecyclerViewAdapter todoListRecyclerViewAdapter;

    private List<ToDo> listData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodoListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TodoListFragment newInstance(int columnCount) {
        TodoListFragment fragment = new TodoListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoViewModel = new TodoViewModel(this.getActivity().getApplication());
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        // Set the adapter
        if (view.findViewById(R.id.recyclerview_todo_list) instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_todo_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            listData = todoViewModel.getTodoItems();
            todoListRecyclerViewAdapter = new TodoListRecyclerViewAdapter(listData, mListener);
            DividerItemDecoration itemDecor = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
            itemDecor.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(view.getContext(), R.drawable.line_divider)));

            recyclerView.addItemDecoration(itemDecor);

            recyclerView.setAdapter(todoListRecyclerViewAdapter);

        }
        return view;
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnToDoListFragmentInteractionListener) {
            mListener = (OnToDoListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnToDoListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onToDoListFragmentInteraction(ToDo item, ToDoStatus status);
    }

    public void RefreshData(Boolean isGetData) {
        if (isGetData)
            GetData();
        if (todoListRecyclerViewAdapter != null) {
            todoListRecyclerViewAdapter.RefreshData(listData);
            todoListRecyclerViewAdapter.notifyDataSetChanged();

        }

    }


    public void GetData() {
        listData = todoViewModel.getTodoItems();

    }

    public void setFilter(FilterObject filter) {

        List<ToDo> filteredData = new ArrayList<>();
        List<ToDo> dbData = todoViewModel.getTodoItems();

        if (filter.isNotFilter()) {
            listData = dbData;
            RefreshData(false);
            return;
        }

        String query = null;
        if (filter.Name != null && filter.Name.trim().length() > 0)
            query = filter.Name.trim().toLowerCase();

        for (ToDo item : dbData) {
            if ((query == null || item.getName().toLowerCase().contains(query))
                    && (filter.rosterList.size() == 0 || filter.rosterList.contains(item.getRosterId()))) {
                if (filter.Done && item.getStatus().equalsIgnoreCase(ToDoStatus.COMPLETE.toString())) {
                    filteredData.add(item);
                    continue;
                }
                if (filter.Todo && item.getStatus().equalsIgnoreCase(ToDoStatus.TODO.toString())) {
                    filteredData.add(item);
                    continue;
                }
                long date = new Date().getTime();
                if (filter.Expired &&
                        (item.getStatus().equalsIgnoreCase(ToDoStatus.EXPIRED.toString()) || item.getDeadLine() <= date)) {
                    filteredData.add(item);

                }
            }
        }

        RefreshData(false);
        listData = filteredData;

    }

    public void setSortOrder(OrderStatus orderStatus) {

        Collections.sort(listData, new Comparator<ToDo>() {
            @Override
            public int compare(ToDo o1, ToDo o2) {
                switch (orderStatus) {
                    case NAME:
                        return o1.getName().compareTo(o2.getName());

                    case STATUS:
                        return o1.getStatus().compareTo(o2.getStatus());

                    case DEADLINE:
                        return o1.getDeadLine().compareTo(o2.getDeadLine());

                    case CREATEDATE:
                        return o1.getCreateDate().compareTo(o2.getCreateDate());

                    default:
                        break;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        RefreshData(false);

    }
}
