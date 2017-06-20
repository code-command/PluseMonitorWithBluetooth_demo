package com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.ErrorCode;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.User;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Widget.OptimizationToast;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.R;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.OnGetClickListener;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.RegisterButtonViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ActionBarOperation;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.HideSoftKeyBoard;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ImmersionLine;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActionbarLoginBinding;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActivityRegisterBinding;

import java.lang.ref.WeakReference;

public class RegisterActivity extends AppCompatActivity {

    private User user;
    private RegisterButtonViewModel registerButtonViewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        new ImmersionLine(this, R.color.actionBarBackground);
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        initActionBar();
        initData();
        bindingData();
    }

    private void initActionBar() {
        HideSoftKeyBoard.setupUI(binding.loginRlRegisterRoot, this);
        initCustomActionBar();
    }

    private void initCustomActionBar() {
        ActionBarOperation.setSystemActionBar(getApplicationContext(), this.getSupportActionBar(), R.color.actionBarBackground);
        ViewGroup root = (ViewGroup) findViewById(R.id.ablogin_root);
        ActionbarLoginBinding custonActionBar = DataBindingUtil.inflate(getLayoutInflater(), R.layout.actionbar_login, root, false);
        custonActionBar.abloginTitle.setText(R.string.login_register);
        custonActionBar.abloginLeftImbtn.setVisibility(View.VISIBLE);
        custonActionBar.abloginLeftImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.getSupportActionBar().setCustomView(custonActionBar.abloginRoot);
    }

    private void initData() {
        user = new User();
        registerButtonViewModel = new RegisterButtonViewModel(user);
        registerButtonViewModel.setOnClickListener(new OnGetClickListener() {
            @Override
            public void success() {
                OptimizationToast.showToast(new WeakReference<Context>(getApplication()), getString(R.string.register_succeed));
            }

            @Override
            public void failure(ErrorCode errorCode) {
                OptimizationToast.showToast(new WeakReference<Context>(getApplication()), getString(errorCode.getIndex()));
            }
        });
    }

    private void bindingData () {
        binding.setUser(user);
        binding.setRegisterViewModel(registerButtonViewModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
