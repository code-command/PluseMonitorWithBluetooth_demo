package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel;

import android.content.Context;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Widget.OptimizationToast;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class ExitOperation {
    private static boolean isExit = false;

    public static void exitBy2Click(Context context) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            OptimizationToast.showToast(new WeakReference<Context>(context), context.getResources().getString(R.string.app_exithint));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }
}
