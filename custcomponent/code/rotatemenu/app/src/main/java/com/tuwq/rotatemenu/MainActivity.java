package com.tuwq.rotatemenu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tuwq.rotatemenu.tool.Tool;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mMenu;
    private Button mHome;

    // 标识上部菜单隐藏显示
    private boolean isShowMenuTop = true;
    // 标识中部菜单隐藏显示
    private boolean isShowMenuMiddle = true;
    RelativeLayout mRelTop;
    RelativeLayout mRelMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mMenu = this.findViewById(R.id.menu);
        mHome = this.findViewById(R.id.home);
        mRelTop = this.findViewById(R.id.rel_top);
        mRelMiddle = this.findViewById(R.id.rel_middle);

        // 设置按钮点击事件
        mMenu.setOnClickListener(this);
        mHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                if (Tool.isAnimationStart()) {
                    return;
                }
                // 如果上部的菜单显示,点击隐藏
                // 如果上部的菜单隐藏,点击显示
                if (isShowMenuTop) {
                    Tool.hide(mRelTop, 0L);
                } else {
                    Tool.show(mRelTop);
                }
                isShowMenuTop = !isShowMenuTop;
                break;
            case R.id.home:
                if (Tool.isAnimationStart()) {
                    return;
                }
                // 如果上部菜单展示,点击隐藏上部菜单和中部菜单
                // 如果上部菜单隐藏,中部菜单显示,点击隐藏中部菜单
                // 如果全部隐藏,点击显示中部菜单
                if (isShowMenuTop) {
                    isShowMenuTop = false;
                    Tool.hide(mRelTop, 0L);
                    Tool.hide(mRelMiddle, 300L);
                } else if (isShowMenuMiddle) {
                    Tool.hide(mRelMiddle, 0L);
                } else {
                    Tool.show(mRelMiddle);
                }
                isShowMenuMiddle = !isShowMenuMiddle;
                break;
        }
    }
}
