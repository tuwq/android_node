package com.tuwq.smscollection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] smss = new String[]{"中秋八月中，美景伴人行。愿皎月添你福，桂香增你喜，玉兔伴你乐，嫦娥共你舞。祝愿佳节快乐，花好月圆合家欢",
            "你好，我是财急送的送货员，钟秋洁给你寄了包裹，请验收：一辈子的平安与幸福，甜蜜的爱情与好心情，永远垂青你的好运气。",
            "中秋快到了：送一个如意饼，事事如意；送你一个开心饼，时时开心；送你一个童心饼，世世年轻；送你一个团圆饼，阖家团圆！",
            "嫦娥说：月宫寂寞空相守；吴刚说：这砍树何时是个头；玉兔说：啥时带我去旅游；短信说：诚挚祝福祝中秋。祝：中秋快乐！" ,
            "中秋祝福各不同，幸福快意藏其中，不与他人去争锋，我的问候悄悄送：团圆温馨谱美梦，举杯同庆亲情浓，祝愿中秋好心情！" ,
            "中秋佳节将至，为了保护环境和简约低碳生活，倡议大家不购买、不赠送过度包装的月饼。如果自用请选择简单包装的月饼！" ,
            "月光无形，给人间的是一片浪漫；菊开无声，给大地的是缤纷色彩；我的祝福无言，给你的是情意一片：中秋快乐永远快乐！" ,
            "左手握住健康，右手抓住财富。左腿跨过艰难险阻，右腿踏上成功之路。用力喊出幸福，用心感受我这中秋祝福：中秋快乐！" ,
            "伴着教师节的深情，把美好送给园丁；迎着中秋节的来临，愿好运陪着你旅行；对你的祝福不曾停，衷心祝愿你天天好心情！" ,
            "秋风吹，斜阳照，菊花朵朵对你笑；月儿圆，月饼甜，短信祝福送祝愿。愿你幸福甜蜜乐无边！健康平安到永远！中秋快乐！"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv_list = (ListView) findViewById(R.id.lv_sms);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, smss);
        lv_list.setAdapter(adapter);
        // 为当前listView添加条目点击事件
        lv_list.setOnItemClickListener(new OnItemClickListener() {
            /**
             * 点击事件方法
             * @param parent 哪个listView被点击,这个listView就是parent
             * @param view 被点击的item
             * @param position item的下标位置
             * @param id 被点击的item的布局id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String sms = smss[position];
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("sms_body", sms);
                startActivity(intent);
            }
        });
    }
}
