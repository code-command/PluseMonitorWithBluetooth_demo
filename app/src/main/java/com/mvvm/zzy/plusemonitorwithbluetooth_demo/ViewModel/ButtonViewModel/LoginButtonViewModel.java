package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel;

import android.view.View;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.ErrorCode;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.User;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.DatabaseViewModel.SQLOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class LoginButtonViewModel extends ButtonViewModel {

    private User user;
    private OnGetClickListener onClickListener;

    public LoginButtonViewModel(User user) {
        this.user = user;
    }

    @Override
    public void buttonChangedListener(View view) {
        List<ErrorCode> list = new ArrayList<>();
        if (SQLOperation.checkLogin(user, list)) {
            onClickListener.success();
        } else {
            onClickListener.failure(list.get(list.size()-1));
        }
    }

    public void setOnClickListener(OnGetClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
