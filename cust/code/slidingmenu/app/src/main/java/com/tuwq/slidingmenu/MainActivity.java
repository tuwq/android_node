package com.tuwq.slidingmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuwq.slidingmenu.view.MySlidingMenu;

public class MainActivity extends Activity {

    private ImageView mBack;
    private MySlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mSlidingMenu = (MySlidingMenu) findViewById(R.id.myslidingmenu);
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开关闭侧拉菜单
                mSlidingMenu.toggle();
            }
        });
    }


    /**
     * 菜单页的textView的点击事件
     * @param view 被点击的控件对象
     */
    public void showtext(View view) {
        TextView textView = (TextView) view;
        Toast.makeText(this, textView.getText(), Toast.LENGTH_SHORT).show();
    }
}
