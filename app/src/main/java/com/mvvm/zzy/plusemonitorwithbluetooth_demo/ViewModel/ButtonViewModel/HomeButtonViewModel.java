package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel;

import android.view.View;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.R;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity.HistoryActivity;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity.MoniterActivity;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity.SetActivity;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class HomeButtonViewModel extends ButtonViewModel {

    private OnGetNewActivity onGetNewActivity;

    public void setonGetNewActivity(OnGetNewActivity onGetNewActivity) {
        this.onGetNewActivity = onGetNewActivity;
    }

    @Override
    public void buttonChangedListener(View view) {
        int viewId = view.getId();
        onGetNewActivity.setNewActivity(getNewActivity(view.getId()));
    }

    private Class<?> getNewActivity(int viewId) {
        switch (viewId) {
            case R.id.home_btn_monitor:
                return MoniterActivity.class;

            case R.id.home_btn_history:
                return HistoryActivity.class;

            case R.id.home_btn_set:
                return SetActivity.class;

            default:
                return null;
        }
    }
}
