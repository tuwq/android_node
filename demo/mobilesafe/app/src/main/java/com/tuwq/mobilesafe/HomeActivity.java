package com.tuwq.mobilesafe;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends Activity {

    View mLogo;
    GridView mGridView;

    private final String[] TITLES = new String[] { "手机防盗", "骚扰拦截", "软件管家",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };
    private final String[] DESCS = new String[] { "远程定位手机", "全面拦截骚扰", "管理您的软件",
            "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };
    private final int[] ICONS = new int[] { R.drawable.sjfd, R.drawable.srlj,
            R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
            R.drawable.hcql, R.drawable.cygj };
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mLogo = this.findViewById(R.id.home_iv_logo);
        mGridView  = this.findViewById(R.id.home_gv_gridview);
        // 实现logo旋转动画
        setAnimation();
        // 通过gridView显示数据
        mGridView.setAdapter(new MyAdaptor());
    }

    /**
     * gridView的adaptor
     */
    private class MyAdaptor extends BaseAdapter {
        // 设置条目个数
        @Override
        public int getCount() {
            return ICONS.length;
        }
        // 根据条目的位置获取条目的数据
        @Override
        public Object getItem(int position) {
            return null;
        }
        // 获取条目的id
        @Override
        public long getItemId(int position) {
            return 0;
        }
        // 设置条目的样式
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),
                    R.layout.home_gridview_item, null);

            // 初始化控件，显示内容
            // findviewbyid表示控件要从activity的布局文件中查找，view.findviewbyid表示控件要从条目的布局文件中查找
            ImageView mIcon = (ImageView) view.findViewById(R.id.item_iv_icon);
            TextView mTitle = (TextView) view.findViewById(R.id.item_tv_title);
            TextView mDesc = (TextView) view.findViewById(R.id.item_tv_desc);

            // 显示内容
            mIcon.setImageResource(ICONS[position]);// 根据条目的位置获取相应的图片展示
            mTitle.setText(TITLES[position]);// 根据条目的位置获取相应的标题展示
            mDesc.setText(DESCS[position]);// 根据条目的位置获取相应的描述信息展示展示
            return view;
        }
    }

    /**
     * 设置按钮的点击事件
     * @param view 被点击控件的view对象
     */
    public void enterSetting(View view) {
        // 跳转到设置中心界面
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    /**
     * logo旋转动画
     */
    private void setAnimation() {
        /*mLogo.setRotationX(); // 根据x轴进行旋转
        mLogo.setRotationY(); // 根据y轴进行旋转
        mLogo.setRotation(); // 根据z轴进行旋转*/
        /**
         * arg1: 执行动画的控件
         * arg2: 执行动画的方法的名称
         * arg3: 执行动画所需的参数
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLogo, "rotationY", 0f, 90f, 270f, 360f);
        objectAnimator.setDuration(2000); // 设置持续时间
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE); // 设置动画执行次数 INFINITE一直执行
        // RESTART 每次旋转从开始的位置旋转
        // REVERSE 每次旋转从结束的位置旋转
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);// 设置动画执行类型
        objectAnimator.start(); // 执行动画
    }
}
