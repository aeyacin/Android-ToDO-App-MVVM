package com.aeyacin.todolist.ui.addtodo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;
import com.aeyacin.todolist.databinding.ActivityAddtodoBinding;
import com.aeyacin.todolist.model.ProcessType;
import com.aeyacin.todolist.ui.base.ActivityListener;
import com.aeyacin.todolist.ui.base.BaseActivity;
import com.aeyacin.todolist.utils.KeyboardHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class AddToDoActivity extends BaseActivity implements ActivityListener {

    public static String Edit_Key = "ADDKEY";
    private AddToDoViewModel mViewModel;

    @BindView(R.id.spinner_roster)
    public Spinner categorySpinner;

    @BindView(R.id.todo_date)
    public EditText dateTodo;

    @BindView(R.id.todo_time)
    public EditText timeTodo;

    private List<ToDoRoster> categoriList;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityAddtodoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_addtodo);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(AddToDoViewModel.class);

        binding.setViewmodel(mViewModel);
        mViewModel.setActivityListener(this);


        Intent i = getIntent();
        if (i.hasExtra(Edit_Key) && i.getExtras() != null) {
            try {
                ToDo todo = (ToDo) i.getSerializableExtra(Edit_Key);
                mViewModel.Edit(todo);
            } catch (Exception ignored) {
            }


        }
        AddSpinnerData();

        calendar = Calendar.getInstance();

        dateTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardHelper.hideKeyboard((EditText) v);
                calendar = mViewModel.getCalendar();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint({"SetTextI18n", "DefaultLocale"})
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month += 1;
                                mViewModel.SetDate(year, month, dayOfMonth);
                                dateTodo.setText((String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month) + "/" + year));

                            }
                        }, year, month, dayOfMonth);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, v.getContext().getString(R.string.dialog_select), dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, v.getContext().getString(R.string.dialog_cancel), dpd);
                dpd.show();
            }
        });
        timeTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardHelper.hideKeyboard((EditText) v);

                calendar = mViewModel.getCalendar();

                int saat = calendar.get(Calendar.HOUR_OF_DAY);
                int dakika = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint({"SetTextI18n", "DefaultLocale"})
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mViewModel.SetTime(hourOfDay, minute, -1);
                                timeTodo.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));

                            }
                        }, saat, dakika, true);

                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, v.getContext().getString(R.string.dialog_select), tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, v.getContext().getString(R.string.dialog_cancel), tpd);
                tpd.show();
            }
        });

    }

    public void AddSpinnerData() {
        categoriList = mViewModel.getRosterList();
        List<String> categoryNames = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < categoriList.size(); i++) {
            categoryNames.add(categoriList.get(i).getName());
            if (mViewModel.RosterID.getValue() != null && mViewModel.RosterID.getValue() > 0 && mViewModel.RosterID.getValue() == categoriList.get(i).getId())
                position = i;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        categorySpinner.setSelection(position);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.RosterID.setValue(categoriList.get(position).getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess(Boolean state) {
        Intent returnIntent = new Intent();
        if (state)
            setResult(Activity.RESULT_OK, returnIntent);
        else
            setResult(Activity.RESULT_CANCELED, returnIntent);

        finish();

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onProcess(ProcessType processType, Object data) {
        ToDo item = (ToDo) data;

    }


}
