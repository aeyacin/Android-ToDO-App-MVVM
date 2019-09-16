package com.aeyacin.todolist.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.ToDoApplication;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;
import com.aeyacin.todolist.data.repositories.ToDoRepository;
import com.aeyacin.todolist.model.FilterObject;
import com.aeyacin.todolist.model.OrderStatus;
import com.aeyacin.todolist.model.ToDoStatus;
import com.aeyacin.todolist.ui.addtodo.AddToDoActivity;
import com.aeyacin.todolist.ui.auth.AuthViewModel;
import com.aeyacin.todolist.ui.auth.AuthViewModelFactory;
import com.aeyacin.todolist.ui.base.BaseActivity;
import com.aeyacin.todolist.ui.home.todolist.CheckRosterAdapter;
import com.aeyacin.todolist.ui.home.todolist.TodoListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import petrov.kristiyan.colorpicker.ColorPicker;

public class HomeActivity extends BaseActivity implements TodoListFragment.OnToDoListFragmentInteractionListener {

    private AuthViewModel loginViewModel;
    private ToDoRepository repository;
    int RequestCode = 9091;

    private List<ToDoRoster> rosterDataList = new ArrayList<>();
    private TodoListFragment todoListFragment;
    private OrderStatus lastOrder = OrderStatus.NAME;
    private FilterObject filterObject = new FilterObject();
    int finalColor = Color.BLUE;

    @BindView(R.id.filter_search)
    public SearchView searchView;

    @BindView(R.id.checkCategory)
    public ListView rosterListView;

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        loginViewModel = ViewModelProviders.of(this, getFactory()).get(AuthViewModel.class);
        repository = new ToDoRepository(this.getApplication());


        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(view -> {
            //Add New ToDo List
            startActivityForResult(new Intent(HomeActivity.this, AddToDoActivity.class), RequestCode);
        });

        FloatingActionButton fabFilter = findViewById(R.id.fab_filter);
        fabFilter.setOnClickListener(view -> {
            //popup filter To Do List
            showSortPopup();
        });

        if (savedInstanceState == null) {
            todoListFragment = TodoListFragment.newInstance(1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mycontent, todoListFragment)
                    .commit();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                filterObject.Name = query;
                setFilter();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterObject.Name = newText;
                return false;
            }
        });
        setRosterCheckedList();
        drawerLayout.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (DragEvent.ACTION_DRAG_EXITED == event.getAction())
                    setFilter();
                return false;
            }
        });

    }

    private void setRosterCheckedList() {
        rosterDataList = repository.getTodoRosterList();
        CheckRosterAdapter checkRosterAdapter = new CheckRosterAdapter(HomeActivity.this, rosterDataList);
        rosterListView.setAdapter(checkRosterAdapter);

    }


    private AuthViewModelFactory getFactory() {
        return new AuthViewModelFactory(this.getApplication());
    }



    @Override
    public void onToDoListFragmentInteraction(ToDo item, ToDoStatus status) {
        switch (status) {
            case COMPLETE:
//update set Complete
                break;
            case EDIT:
                Intent intent = new Intent(HomeActivity.this, AddToDoActivity.class);
                intent.putExtra(AddToDoActivity.Edit_Key, item);
                startActivityForResult(intent, RequestCode);
                break;
            case EXPIRED:
//Update set Expired
                break;
            default:
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                RefreshData();
            }
        }
    }


    @SuppressLint("RtlHardcoded")
    public void openMenu(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeMenu(View view) {
        drawerLayout.closeDrawers();
        //Refresh todo List
        setFilter();
    }

    /**
     * Refresh All data
     */
    private void RefreshData() {
        todoListFragment.RefreshData(true);
        rosterDataList = repository.getTodoRosterList();
        ((CheckRosterAdapter) rosterListView.getAdapter()).notifyDataSetChanged();
    }


    private void setFilter() {

        for (ToDoRoster item : rosterDataList) {
            if (item.isShow())
                filterObject.rosterList.add(item.getId());
        }

        filterObject.Expired = ((CheckBox) findViewById(R.id.check_expired)).isChecked();
        filterObject.Todo = ((CheckBox) findViewById(R.id.check_todo)).isChecked();
        filterObject.Done = ((CheckBox) findViewById(R.id.check_done)).isChecked();


        todoListFragment.setFilter(filterObject);
    }

    public void showSortPopup() {
        View menuItemView = findViewById(R.id.fab_filter);
        PopupMenu popup = new PopupMenu(this, menuItemView, Gravity.CENTER);
        popup.inflate(R.menu.sort);


        if (lastOrder == OrderStatus.NAME)
            popup.getMenu().findItem(R.id.sort_name).setChecked(true);
        else if (lastOrder == OrderStatus.STATUS)
            popup.getMenu().findItem(R.id.sort_status).setChecked(true);
        else if (lastOrder == OrderStatus.DEADLINE)
            popup.getMenu().findItem(R.id.sort_deadline).setChecked(true);
        else if (lastOrder == OrderStatus.CREATEDATE)
            popup.getMenu().findItem(R.id.sort_createdate).setChecked(true);


        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sort_name:
                    lastOrder = OrderStatus.NAME;
                    todoListFragment.setSortOrder(OrderStatus.NAME);
                    item.setChecked(!item.isChecked());
                    break;
                case R.id.sort_status:
                    lastOrder = OrderStatus.STATUS;
                    todoListFragment.setSortOrder(OrderStatus.STATUS);
                    item.setChecked(!item.isChecked());
                    ;
                    break;
                case R.id.sort_deadline:
                    lastOrder = OrderStatus.DEADLINE;
                    todoListFragment.setSortOrder(OrderStatus.DEADLINE);
                    item.setChecked(!item.isChecked());

                    break;
                case R.id.sort_createdate:
                    lastOrder = OrderStatus.CREATEDATE;
                    todoListFragment.setSortOrder(OrderStatus.CREATEDATE);
                    item.setChecked(!item.isChecked());

                    break;
                default:
                    break;
            }
            return false;
        });
        popup.show();
    }


    public void logout(View view) {
        loginViewModel.onLogout(view);
        finish();
    }

    //region Roster metods
    public void rosterChecked(View view) {
        final int position = rosterListView.getPositionForView((View) view.getParent());
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked())
            rosterDataList.get(position).setShow(true);
        else
            rosterDataList.get(position).setShow(false);
    }

    public void addCategory(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_add_roster_item);
        dialog.setTitle(getString(R.string.add_todo_list));
        dialog.findViewById(R.id.color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryColor(view, dialog);
            }
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAvaible = false;
                String name = ((TextView) dialog.findViewById(R.id.catName)).getText().toString();
                for (ToDoRoster item : rosterDataList) {
                    if (item.getName().equalsIgnoreCase(name)) {
                        isAvaible = true;
                        break;
                    }
                }

                if (!isAvaible) {

                    ToDoRoster toDoRoster = new ToDoRoster();
                    toDoRoster.setUserId(ToDoApplication.getSession().getUserId());
                    toDoRoster.setName(name);
                    toDoRoster.setColor(finalColor);
                    toDoRoster.setDescription("");
                    toDoRoster.setShow(true);
                    toDoRoster.setCreateDate(new Date().getTime());
                    repository.addTodoRoster(toDoRoster);
                    RefreshData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Error: Category " + name + " already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


    private void setCategoryColor(View v, final Dialog dialog) {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                if (color != 0)
                    finalColor = color;
                else
                    finalColor = Color.parseColor("#262D3B");
                //((View) findViewById(R.id.color)).setBackgroundColor(color);
                dialog.findViewById(R.id.color).setBackgroundColor(finalColor);
            }

            @Override
            public void onCancel() {
                //here goes nothing
            }
        });
    }

    public void deleteRoster(View v) {
        final int position = rosterListView.getPositionForView((View) v.getParent());
        if (!rosterDataList.get(position).getName().equalsIgnoreCase("none")) {
            repository.deleteTodoRoster(rosterDataList.get(position));
            repository.setAutoRoster();
            RefreshData();
        } else
            Toast.makeText(getApplicationContext(), getString(R.string.message_not_delete_category_none), Toast.LENGTH_SHORT).show();
    }

    //endregion

}
