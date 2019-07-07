package com.tuwq.mobilesafe;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuwq.mobilesafe.utils.MD5Util;
import com.tuwq.mobilesafe.utils.SystemConstants;
import com.tuwq.mobilesafe.utils.SharedPreferencesUtil;

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {

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
        // 设置gridView的条目点击事件
        mGridView.setOnItemClickListener(this);
    }

    /**
     * gridview的条目点击事件
     * @param parent
     * @param view 被点击条目的view对象
     * @param position 被点击的条目的索引（位置）
     * @param id 被点击的条目的id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)  {
        switch (position) {
            case 0:
                // 手机防盗
                // 弹出设置密码对话框
                // showSetPassWordDialog();
                // 判断是弹出设置密码对话框还是验证密码对话框
                // 问题：需要知道密码是否设置成功
                // 当设置密码成功，保存密码，再次点击手机防盗条目的时候，获取保存的密码，如果有保存，弹出验证密码对话框，如果没有弹出设置密码对话框
                String sp_psw = SharedPreferencesUtil.getString(
                        getApplicationContext(), SystemConstants.SJFDPSW, "");
                if (TextUtils.isEmpty(sp_psw)) {
                    showSetPassWordDialog();
                } else {
                    // 验证密码
                    showEnterPassWordDialog();
                }
                break;
        }
    }

    /**
     * 弹出验证密码对话框
     */
    private void showEnterPassWordDialog() {
        AlertDialog.Builder builder = new Builder(this);
        View view = View.inflate(getApplicationContext(),
                R.layout.home_enterpassword_dialog, null);
        // 初始化控件
        final EditText mPsw = (EditText) view.findViewById(R.id.dialog_et_psw);
        Button mOk = (Button) view.findViewById(R.id.dialog_ok);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);
        // 设置按钮点击事件
        mOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.获取输入的密码
                String psw = mPsw.getText().toString().trim();
                if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
                    return;
                }
                //2.获取保存的密码，判断输入密码是否正确
                String sp_psw = SharedPreferencesUtil.getString(getApplicationContext(), SystemConstants.SJFDPSW, "");
                //因为md5加密不可逆，所以比较的时候，将输入的密码再次加密，让两个密文比较
                if (MD5Util.msgToMD5(psw).equals(sp_psw)) {
                    Toast.makeText(getApplicationContext(), "密码正确", 0).show();
                    dialog.dismiss();
                    //跳转到手机防盗设置向导的第一个界面
                    enterLostFind();
                }else{
                    Toast.makeText(getApplicationContext(), "密码错误", 0).show();
                }
            }
        });
        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏对话框
                dialog.dismiss();
            }
        });
        builder.setView(view);// 将一个view对象添加到对话框中显示
        // builder.show();
        dialog = builder.create();
        dialog.show();
    }

    /**
     * 跳转到手机防盗设置向导第一个界面
     */
    protected void enterLostFind() {
        //获取是否设置成功的标示，保存：跳转到手机防盗信息展示页面，没有保存：跳转到设置向导页面
        boolean b = SharedPreferencesUtil.getBoolean(getApplicationContext(), SystemConstants.ISSJFDSETTING, false);
        if (b) {
            Intent intent = new Intent(this,LostFindActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,SetUp1Activity.class);
            startActivity(intent);
        }
    }


    /**
     * 弹出设置密码对话框
     */
    private void showSetPassWordDialog() {
        AlertDialog.Builder builder = new Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.home_setpassword_dialog, null);
        // 初始化控件
        final EditText mPsw = (EditText) view.findViewById(R.id.dialog_et_psw);
        final EditText mConfirm = (EditText) view.findViewById(R.id.dialog_et_confirm);
        Button mOk = (Button) view.findViewById(R.id.dialog_ok);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);
        // 设置按钮点击事件
        mOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.获取输入的密码
                String psw = mPsw.getText().toString().trim();
                // 判断密码是否为空
                if (TextUtils.isEmpty(psw)) {// null ""
                    Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
                    return;
                }
                // 2.获取再次输入的密码，判断两次密码是否一致
                String confirm = mConfirm.getText().toString().trim();
                if (psw.equals(confirm)) {
                    Toast.makeText(getApplicationContext(), "密码设置成功", 0).show();
                    // 隐藏对话框
                    dialog.dismiss();
                    // 保存设置的密码，为再次点击手机防盗条目判断做准备
                    SharedPreferencesUtil.saveString(getApplicationContext(),
                            SystemConstants.SJFDPSW,MD5Util.msgToMD5(psw));
                } else {
                    Toast.makeText(getApplicationContext(), "两次密码不一致", 0)
                            .show();
                }
            }
        });
        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏对话框
                dialog.dismiss();
            }
        });
        builder.setView(view);// 将一个view对象添加到对话框中显示
        // builder.show();
        dialog = builder.create();
        dialog.show();
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
