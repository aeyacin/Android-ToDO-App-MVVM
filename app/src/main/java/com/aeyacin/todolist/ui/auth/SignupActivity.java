package com.aeyacin.todolist.ui.auth;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.aeyacin.todolist.R;
import com.aeyacin.todolist.data.db.entities.User;
import com.aeyacin.todolist.databinding.ActivitySignupBinding;
import com.aeyacin.todolist.ui.base.BaseActivity;
import com.aeyacin.todolist.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity implements AuthListener {

    @BindView(R.id.progress_bar)
    public ProgressBar progress_bar;

    @BindView(R.id.root_layout)
    public CoordinatorLayout coordinatorLayout;

    private AuthViewModelFactory getFactory() {
        return new AuthViewModelFactory(this.getApplication());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        ButterKnife.bind(this);

        AuthViewModel loginViewModel = ViewModelProviders.of(this, getFactory()).get(AuthViewModel.class);
        binding.setViewmodel(loginViewModel);
        loginViewModel.setAuthListener(this);


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
