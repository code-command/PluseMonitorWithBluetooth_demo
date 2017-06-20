package com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.ErrorCode;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.User;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Widget.OptimizationToast;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.R;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.LoginButtonViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.OnGetClickListener;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.EditTextViewModel.UserViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ActionBarOperation;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ExitOperation;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.HideSoftKeyBoard;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ImmersionLine;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActionbarLoginBinding;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActivityLoginBinding;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {

    private User user;
    private UserViewModel userViewModel;
    private LoginButtonViewModel loginButtonViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        new ImmersionLine(this, R.color.actionBarBackground);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        initActionBar();
        initData();
        bindingData();
    }

    private void initActionBar() {
        HideSoftKeyBoard.setupUI(binding.loginRlRoot, this);
        initCustomActionBar();
    }

    private void initCustomActionBar() {
        ActionBarOperation.setSystemActionBar(getApplicationContext(), this.getSupportActionBar(), R.color.actionBarBackground);
        ViewGroup root = (ViewGroup) findViewById(R.id.ablogin_root);
        ActionbarLoginBinding custonActionBar = DataBindingUtil.inflate(getLayoutInflater(), R.layout.actionbar_login, root, false);
        custonActionBar.abloginTitle.setText(getString(R.string.login_login));
        custonActionBar.abloginRightImbtn.setVisibility(View.VISIBLE);
        custonActionBar.abloginRightImbtn.setText(R.string.login_register);
        custonActionBar.abloginRightImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        this.getSupportActionBar().setCustomView(custonActionBar.abloginRoot);
    }

    private void initData() {
        user = new User();
        userViewModel = new UserViewModel(user);
        loginButtonViewModel = new LoginButtonViewModel(user);
        loginButtonViewModel.setOnClickListener(new OnGetClickListener() {
            @Override
            public void success() {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }

            @Override
            public void failure(ErrorCode errorCode) {
                OptimizationToast.showToast(new WeakReference<Context>(getApplication()), getString(errorCode.getIndex()));
            }
        });

    }

    private void bindingData() {
        binding.setUserViewModel(userViewModel);
        binding.setLoginViewModel(loginButtonViewModel);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            ExitOperation.exitBy2Click(getApplicationContext());
        }
        return false;
    }
}


