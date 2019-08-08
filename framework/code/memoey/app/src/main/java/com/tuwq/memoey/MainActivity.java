package com.tuwq.memoey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
    }

    public void click(View v){
        //monitor
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        img.setImageBitmap(bitmap);
    }
}
