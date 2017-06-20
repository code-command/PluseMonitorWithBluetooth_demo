package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel;

import android.view.View;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.ErrorCode;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.User;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.DatabaseViewModel.SQLOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class RegisterButtonViewModel extends ButtonViewModel {

    private User user;
    private OnGetClickListener onClickListener;

    public RegisterButtonViewModel(User user) {
        this.user = user;
    }

    public void setOnClickListener(OnGetClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void buttonChangedListener(View view) {
        List<ErrorCode> list = new ArrayList<>();
        if (SQLOperation.registerUser(user, list)) {
            onClickListener.success();
        } else {
            onClickListener.failure(list.get(list.size()-1));
        }
    }
}
