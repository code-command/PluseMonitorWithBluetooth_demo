package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ListViewModel;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class NavigationListViewModel implements AdapterView.OnItemClickListener {

    private OnChangedFragment onChangedFragment;

    public void setOnChangedFragment(OnChangedFragment onChangedFragment) {
        this.onChangedFragment = onChangedFragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                onChangedFragment.getNewFragment("实时诊断");
                break;

            case 1:
                onChangedFragment.getNewFragment("历史数据");
                break;

            case 2:
                onChangedFragment.getNewFragment("我的设置");
                break;

            case 3:
                onChangedFragment.getNewFragment("注销");
                break;

            default:
                break;
        }
    }
}
