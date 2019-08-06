package com.tuwq.slidemenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView menu_listview = (ListView) findViewById(R.id.menu_listview);
        ListView main_listview = (ListView) findViewById(R.id.main_listview);
        SlideMenu slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        final ImageView iv_head = (ImageView) findViewById(R.id.iv_head);


        //快速填充数据
        main_listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
                ,Constant.NAMES));
        menu_listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
                ,Constant.sCheeseStrings){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });

        slideMenu.setOnSlideChangeListener(new SlideMenu.OnSlideChangeListener() {
            @Override
            public void onOpen() {
                Toast.makeText(MainActivity.this,"open",0).show();

            }
            @Override
            public void onClose() {
                Toast.makeText(MainActivity.this,"close",0).show();
                //让小孩儿执行动画
                ViewCompat.animate(iv_head).translationXBy(50)
                        .setInterpolator(new CycleInterpolator(4))
                        .setDuration(800).start();
            }

            @Override
            public void onDraging(float fraction) {
                //0-1
                iv_head.setRotation(720*fraction);
            }
        });

    }
}
