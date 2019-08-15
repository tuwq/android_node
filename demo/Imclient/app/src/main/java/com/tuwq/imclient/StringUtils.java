package com.tuwq.imclient;

import android.text.TextUtils;

public class StringUtils {
    /**
     * 校验用户名合法性 字母开头 只能是字符 长度4-18位
     * @param username
     * @return
     */
    public static boolean CheckUsername(String username){
        if(TextUtils.isEmpty(username)){
            //如果没有输入
            return false;
        }else{
            return username.matches("^[a-zA-Z]\\w{4,18}$");
        }
    }

    /**
     * 校验密码合法性 数字 长度4-18位
     * @param pwd
     * @return
     */
    public static boolean Checkpwd(String pwd){
        if(TextUtils.isEmpty(pwd)){
            //如果没有输入
            return false;
        }else{
            return pwd.matches("^[0-9]{4,18}$");
        }
    }
}
