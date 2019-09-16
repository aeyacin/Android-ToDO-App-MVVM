package com.aeyacin.todolist.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.data.db.entities.LoginTable;
import com.aeyacin.todolist.data.db.entities.User;
import com.aeyacin.todolist.databinding.ActivityLoginBinding;
import com.aeyacin.todolist.ui.base.BaseActivity;
import com.aeyacin.todolist.ui.home.HomeActivity;
import com.aeyacin.todolist.utils.ViewUtils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements AuthListener {


    @BindView(R.id.progress_bar)
    public ProgressBar progress_bar;

    @BindView(R.id.root_layout)
    public CoordinatorLayout coordinatorLayout;

    private AuthViewModelFactory getFactory() {
        return new AuthViewModelFactory(this.getApplication());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);

        AuthViewModel loginViewModel = ViewModelProviders.of(this, getFactory()).get(AuthViewModel.class);
        binding.setViewmodel(loginViewModel);
        loginViewModel.setAuthListener(this);


        loginViewModel.getLoggedInUser().observe(this, new Observer<List<LoginTable>>() {
            @Override
            public void onChanged(@Nullable List<LoginTable> data) {

                if (data != null && data.size() > 0) {
                    LoginTable loginTable = data.get(0);
                    if (loginTable.getIsAutoLogin()) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    try {

                        binding.username.setText((Objects.requireNonNull(loginTable).getEmail()));
                        binding.password.setText((Objects.requireNonNull(loginTable.getPassword())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        loginViewModel.getLoggedInUser().observe(this, user -> {

        });

    }



    @Override
    public void onStarted() {
        ViewUtils.show(progress_bar);

    }

    @Override
    public void onSuccess(User user) {
        ViewUtils.hide(progress_bar);


    }

    @Override
    public void onFailure(String message) {
        ViewUtils.snackbar(coordinatorLayout, message);


    }


}
