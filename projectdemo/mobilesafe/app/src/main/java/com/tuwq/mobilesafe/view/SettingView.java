package com.tuwq.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tuwq.mobilesafe.R;

public class SettingView extends RelativeLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.tuwq.mobilesafe";
    private TextView mTitle;
    private ImageView mIcon;

    /**保存开关状态**/
    private boolean mIsToggle;

    public SettingView(Context context) {
        // super(context);
        this(context,null);
    }
    public SettingView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        //  this(context,attrs,-1);
        this(context,attrs,-1);
    }
    public SettingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 将包含有textview和imageView布局文件添加到自定义控件中显示
        initView();

        // 将设置给自定义控件的自定义属性的值获取出来，设置给自定义控件中的textview显示
        // 因为AttributeSet中保存有控件的所有属性，可以通过AttributeSet获取自定义属性的值
        // 通过命名空间和属性的名称，将属性的值获取出来
        String title = attrs.getAttributeValue(NAMESPACE, "title");
        // 将获取的属性的值设置给textview展示
        mTitle.setText(title);
        // 获取隐藏显示图片的属性的值
        /**
         * arg1: 命名空间
         * arg2: 属性的名称
         * arg3: 默认的值，如果没有获取到属性的值，应该使用什么值
         */
        boolean b = attrs.getAttributeBooleanValue(NAMESPACE, "istoggle", true);
        // 根据属性的值设置图片的隐藏和显示了
        mIcon.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    /**
     * 添加自定义控件
     */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.settingview, null);
        this.addView(view);//将view对象添加到自定义控件中

        //初始化控件
        mTitle = (TextView) view.findViewById(R.id.setting_tv_title);
        mIcon = (ImageView) view.findViewById(R.id.setting_iv_icon);
    }

    /**
     * 提供给activity调用的方法，根据传递过来的开关状态，设置自定义控件中的图片的显示类型
     *@param isToggle ： 开关状态
     */
    public void setToggleOn(boolean isToggle){
        // 保存开关状态，方便返回方法使用
        mIsToggle = isToggle;
        if (isToggle) {
            mIcon.setImageResource(R.drawable.on);
        }else{
            mIcon.setImageResource(R.drawable.off);
        }
    }
    /**
     * 提供给activity调用的方法，用来返回开关状态
     *@return
     */
    public boolean istoggle(){
        return mIsToggle;
    }
    /**
     * 提供给activity调用的方法,开关操作
     */
    public void toggle(){
        setToggleOn(!mIsToggle);
    }
}
