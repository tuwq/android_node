package com.tuwq.opennetlib.img;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.tuwq.opennetlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huang on 2016/12/10.
 */

public class ImageActivity extends Activity {
    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ButterKnife.bind(this);

        listview.setAdapter(new MyAdapter());
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Constants.IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view = View.inflate(getApplicationContext(), R.layout.item, null);
            }
//            ImageView img = (ImageView) view.findViewById(R.id.img);
            SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.img);
            // 使用Glide
            // 初始化Glide 图片格式 Argb.565 2
           /* Glide.with(ImageActivity.this)
                    .load(Constants.IMAGES[i])// 设置图片地址
                    .placeholder(R.drawable.place)// 设置默认显示的图片
                    .error(R.drawable.error)// 设置加载失败显示的图片
                    .centerCrop()// 图片按照比例缩放，显示图片中间的内容
                    .crossFade(3000)// 设置渐进时间
                    .into(img);// 显示的控件*/

            // 使用Picaso，默认不压缩，显示的图片清晰，耗内存，图片格式 Argb.8888 4
            /*Picasso.with(ImageActivity.this)
                    .load(Constants.IMAGES[i])// 设置图片地址
                    .placeholder(R.drawable.place)// 设置默认显示的图片
                    .error(R.drawable.error)// 设置加载失败显示的图片
                    .centerCrop()// 图片按照比例缩放，显示图片中间的内容
                    .resize(100,100)// 使用centerCrop时，必须调用resize方法指定图片的宽高
//                    .crossFade(3000)// 设置渐进时间
                    .into(img);// 显示的控件*/
            // 使用Fresco
            // 点击重新加载
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Constants.IMAGES[i])
                    .setTapToRetryEnabled(true)
                    .setOldController(img.getController())
                    .build();

            img.setController(controller);
//            img.setImageURI(Constants.IMAGES[i]);
            return view;
        }
    }
}

