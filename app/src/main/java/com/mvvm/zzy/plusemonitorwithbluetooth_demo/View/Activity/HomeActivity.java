package com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.User;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.R;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.HomeButtonViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.OnGetNewActivity;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ActionBarOperation;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ImmersionLine;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActionbarLoginBinding;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private User user;
    private HomeButtonViewModel homeButtonViewModel;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        new ImmersionLine(this, R.color.actionBarBackground);
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        initActionBar();
        initData();
        bindingData();
    }

    private void initActionBar() {
        initCustomActionBar();
    }

    private void initCustomActionBar() {
        ActionBarOperation.setSystemActionBar(getApplicationContext(), this.getSupportActionBar(), R.color.actionBarBackground);
        ViewGroup root = (ViewGroup) findViewById(R.id.ablogin_root);
        ActionbarLoginBinding custonActionBar = DataBindingUtil.inflate(getLayoutInflater(), R.layout.actionbar_login, root, false);
        custonActionBar.abloginTitle.setText(R.string.app_name);
        custonActionBar.abloginLeftImbtn.setVisibility(View.VISIBLE);
        custonActionBar.abloginLeftImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        this.getSupportActionBar().setCustomView(custonActionBar.abloginRoot);
    }

    private void logout() {
        AlertDialog msetStringDialog = new AlertDialog.Builder(HomeActivity.this)
                .setCancelable(false)
                .setMessage(R.string.navigation_optname_logout)
                .setPositiveButton(R.string.units_options_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(R.string.units_options_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
        msetStringDialog.setCanceledOnTouchOutside(true);
        msetStringDialog.show();
    }

    private void initData() {
        user = (User) getIntent().getSerializableExtra("User");
        homeButtonViewModel = new HomeButtonViewModel();
        homeButtonViewModel.setonGetNewActivity(new OnGetNewActivity() {
            @Override
            public void setNewActivity(Class<?> cls) {
                Intent intent = new Intent(HomeActivity.this, cls);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
    }

    private void bindingData() {
        binding.setHomeButtonViewModel(homeButtonViewModel);
    }
}
