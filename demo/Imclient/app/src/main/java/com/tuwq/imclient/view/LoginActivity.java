package com.tuwq.imclient.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuwq.imclient.MainActivity;
import com.tuwq.imclient.R;
import com.tuwq.imclient.presenter.LoginPresenter;
import com.tuwq.imclient.presenter.LoginPresenterImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView{
    @InjectView(R.id.iv_avatar)
    ImageView ivAvatar;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.til_username)
    TextInputLayout tilUsername;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.tv_newuser)
    TextView tvNewuser;

    LoginPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //有底部导航栏的手机 虚拟home键 如果加上下面一句 底部的控制栏看不清了
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        //获取缓存的用户名密码
        if(getPwd()!=null&&getPwd().trim().length()>0)
            etPwd.setText(getPwd());
        if(getUsername()!=null&&getUsername().trim().length()>0)
            etUsername.setText(getUsername());

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // showToast("onNewIntent");
        //获取缓存的用户名密码
        if(getPwd()!=null&&getPwd().trim().length()>0)
            etPwd.setText(getPwd());
        if(getUsername()!=null&&getUsername().trim().length()>0)
            etUsername.setText(getUsername());
    }

    @OnClick({R.id.btn_login, R.id.tv_newuser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showProgressDialog("正在登录... ...");
                presenter.login(etUsername.getText().toString(),
                        etPwd.getText().toString());
                break;
            case R.id.tv_newuser:
                startActivity(RegistActivity.class,false);
                break;
        }
    }

    @Override
    public void onGetLoginState(String username, boolean isLogin, String errorMsg) {
        cancelProgressDialog();
        if(isLogin)
            //如果登录成功跳到主界面
            startActivity(MainActivity.class,true);
        else{
            //如果登录失败弹吐司
            showToast(errorMsg);
        }

    }
}
