package com.tuwq.parallax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParallaxListView  listview = (ParallaxListView) findViewById(R.id.listview);
        //去掉listveiw滑动到头的时候边缘的蓝色阴影
        listview.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

        View headerView = View.inflate(this,R.layout.header,null);
        ImageView iv_image = (ImageView) headerView.findViewById(R.id.iv_image);
        listview.setParallaxImageView(iv_image);

        listview.addHeaderView(headerView);

        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
                ,Constant.NAMES));
    }
}
