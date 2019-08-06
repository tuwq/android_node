package com.tuwq.quickindex;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Friend> friends = new ArrayList<>();
    private TextView tv_currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuickIndexBar quickIndeBar = (QuickIndexBar) findViewById(R.id.quickIndeBar);
        final ListView listview = (ListView) findViewById(R.id.listview);
        tv_currentWord = (TextView) findViewById(R.id.tv_currentWord);

        quickIndeBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //1.先去集合中寻找首字母为letter的条目，然后将其置顶
                for (int i = 0; i < friends.size(); i++) {
                    String firstWord = friends.get(i).pinyin.charAt(0)+"";
                    if(firstWord.equals(letter)){
                        //说明找到了，那么直接置顶即可
                        listview.setSelection(i);
                        break;//找到立即中断
                    }
                }
                //显示当前按下的字母
                showCurrentWord(letter);
            }
        });

        //准备数据
        prepareData();
        //排序
        Collections.sort(friends);


        //填充listview
        listview.setAdapter(new FriendAdapter(friends));

//        Log.e("tag",PinYinUtil.getPinYin("刘 德 华"));//LIUDEHUA
//        Log.e("tag",PinYinUtil.getPinYin("刘德，。？华"));//LIUDEHUA
//        Log.e("tag",PinYinUtil.getPinYin("刘德1acabsd华"));//LIUDE1acabsdHUA

    }

    Handler handler = new Handler();
    /**
     * 显示当前的字母
     * @param letter
     */
    private void showCurrentWord(String letter) {
        //先清除之前的所有任务和消息
        handler.removeCallbacksAndMessages(null);

        tv_currentWord.setText(letter);
        tv_currentWord.setVisibility(View.VISIBLE);

        //延时隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_currentWord.setVisibility(View.GONE);
            }
        },2000);
    }

    // 虚拟数据
    private void prepareData() {
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }
}
