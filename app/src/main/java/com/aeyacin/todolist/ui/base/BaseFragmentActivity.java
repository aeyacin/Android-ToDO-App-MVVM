package com.aeyacin.todolist.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewLayoutRes());
        setUpInitialFragment(savedInstanceState);

    }

    private void setUpInitialFragment(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            createInitialFragment();

        }


    }

    @LayoutRes
    protected abstract int contentViewLayoutRes();

    @NonNull
    protected abstract void createInitialFragment();
}
