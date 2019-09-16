package com.aeyacin.todolist.utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;


public class ViewUtils {

    public static void show(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hide(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, (CharSequence)message, Toast.LENGTH_LONG).show();
    }

    public static void snackbar(@NotNull View view, @NotNull String message) {
        Snackbar snackbar = Snackbar.make(view, (CharSequence)message, Snackbar.LENGTH_LONG);
        snackbar.setAction((CharSequence) "Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
