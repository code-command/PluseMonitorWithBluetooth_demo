package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.DrawerLayoutViewModel;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class NavigationViewModel implements DrawerLayout.DrawerListener {

    public NavigationViewModel(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    private DrawerLayout drawerLayout;

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        View mContent = drawerLayout.getChildAt(0);
        View mMenu = drawerView;
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale*0.2f;

        if(drawerView.getTag().equals("LEFT")) {
            float leftScale = 1 - 0.3f * scale;
            float leftTransX = 1 - slideOffset;
            float leftTransY = 1;

            ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));

            ViewHelper.setPivotX(mContent, 0);
            ViewHelper.setPivotY(mContent,
                    mContent.getMeasuredHeight() / 2);
            ViewHelper.setTranslationX(mContent,
                    mMenu.getMeasuredWidth() * (1 - scale));

        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
