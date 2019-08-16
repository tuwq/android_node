package com.tuwq.imclient.splash.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tuwq.imclient.Constant;
import com.tuwq.imclient.utils.ToastUtils;

public class BaseActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config",MODE_PRIVATE);
    }

    protected void startActivity(Class clazz,boolean isFinish){
        startActivity(new Intent(getApplicationContext(),clazz));
        if(isFinish){
            finish();
        }
    }

    protected void showToast(String msg){
        ToastUtils.showToast(getApplicationContext(),msg);
    }

    protected void saveUsernamePwd(String username,String pwd){
        sp.edit().putString(Constant.SP_KEY_USERNAME,username).putString(Constant.SP_KEY_PASSWORD,pwd).commit();
    }

    protected String getUsername(){
        return  sp.getString(Constant.SP_KEY_USERNAME,"");
    }

    protected String getPwd(){
        return  sp.getString(Constant.SP_KEY_PASSWORD,"");
    }

    /**
     * 显示进度条对话框
     * @param msg
     */
    protected void showProgressDialog(String msg){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    /**
     * 取消进度条对话框
     */
    protected void cancelProgressDialog(){
        if( progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
