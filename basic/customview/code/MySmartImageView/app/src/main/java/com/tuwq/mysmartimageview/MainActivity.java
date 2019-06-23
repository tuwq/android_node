package com.tuwq.mysmartimageview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    MySmartImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = this.findViewById(R.id.iv_image);
        iv_image.setImageURL("http://blog.img.tuwq.cn/upload/user/avatar/11E68E08859F3D3ED8123CA35AB08B6F.jpg", R.drawable.ic_launcher);
    }

}
