package com.tuwq.fragmentdynamic;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        //
        FragmentManager manager = getFragmentManager();
        // 开启fragment事务
        FragmentTransaction transaction = manager.beginTransaction();

        if(width>height){
            // 横屏
            /**
             * 把fragment对象替换到viewGroup节点下
             * arg1: 用来放置fragment的viewGroup的id
             * arg2: 要显示的fragment对象
             */
            transaction.replace(R.id.fragment_container, new SecondFragment());
        }else{
            // 竖屏
            transaction.replace(R.id.fragment_container, new FirstFragment());
        }
        // 设置完对应的fragment一定要调用commit提交事务
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}
