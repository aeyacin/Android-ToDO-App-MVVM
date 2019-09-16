package com.aeyacin.todolist.utils;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardHelper {

    public static void hideKeyboard(EditText et) {

        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
}
