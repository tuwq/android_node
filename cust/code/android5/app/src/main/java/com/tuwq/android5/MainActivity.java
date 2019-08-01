package com.tuwq.android5;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView icon1 = this.findViewById(R.id.icon1);
        // 裁剪
        icon1.setOutlineProvider(new ViewOutlineProvider() {
            /**
             * @param view 显示阴影的控件
             * @param outline 阴影的操作
             */
            @Override
            public void getOutline(View view, Outline outline) {
                /**
                 * arg1: 开始的位置
                 * arg2: 结束的位置
                 * arg3: 宽
                 * arg4: 高
                 */
                outline.setOval(0,0,view.getWidth(),view.getHeight());
            }
        });
        // 裁剪效果
        TextView text1 = this.findViewById(R.id.text1);
        text1.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        });
        text1.setClipToOutline(true); // 设置裁剪的轮廓操作是否生效

        // 取色操作
        ImageView mIcon2 = this.findViewById(R.id.icon2);
        TextView mText2 = this.findViewById(R.id.text2);
        BitmapDrawable background = (BitmapDrawable) mIcon2.getBackground();
        //通过取色器进行取色操作
        Palette.generateAsync(background.getBitmap(), new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                //getVibrantColor ： 获取图片中最鲜艳的颜色
                mText2.setBackgroundColor(palette.getVibrantColor(Color.BLACK));
            }
        });
    }

}
