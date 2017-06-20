package com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.DatabaseViewModel;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.ErrorCode;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.User;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class SQLOperation {

    public static boolean checkLogin(User user, List<ErrorCode> list) {
        return checkInfoIntegrity(user, list) && verifyUser(user, list);
    }

    private static boolean checkInfoIntegrity(User user, List<ErrorCode> list) {
        if (user.getName()==null || user.getName().isEmpty()) {
            list.add(ErrorCode.EMPTY_USERNAME);
            return false;
        }
        if (user.getPassword()==null || user.getPassword().isEmpty()) {
            list.add(ErrorCode.EMPTY_USERPWD);
            return false;
        }
        return true;
    }

    private static boolean verifyUser (User user, List<ErrorCode> list) {
        /*此代码为验证功能所使用，后期会改成向数据库中查询比对结果*/
        if (user.getName().equals(user.getPassword())) {
            return true;
        } else {
            list.add(ErrorCode.USER_LOGINFAILURE);
            return false;
        }
    }

    public static boolean registerUser(User user, List<ErrorCode> list) {
        return checkInfoIntegrity(user, list) && checkNameUnique(user ,list) && checkPwdFormat(user, list) && saveNewUser(user, list);
    }

    private static boolean checkNameUnique(User user, List<ErrorCode> list) {
        /*此代码为验证功能所使用，后期会改成向数据库中查询并返回查询结果*/
        if (user.getName().equals("errorName")) {
            list.add(ErrorCode.USERNAME_NOTUNIQUE);
            return false;
        }
        return true;
    }

    private static boolean checkPwdFormat(User user, List<ErrorCode> list) {
        /*此代码为验证功能所使用，后期进行争取的非法字符校验*/
        if (user.getPassword().equals("////////")) {
            list.add(ErrorCode.USERPWD_ERRORFORMAT);
            return false;
        }
        return true;
    }

    private static boolean saveNewUser(User user, List<ErrorCode> list) {
        /*此代码为验证功能所使用，后期会改成向数据库中插入数据并返回插入结果*/
        if (user.getName().equals("saveerror")) {
            list.add(ErrorCode.REGISTER_SAVEFAILURE);
            return false;
        }
        return true;
    }
}
