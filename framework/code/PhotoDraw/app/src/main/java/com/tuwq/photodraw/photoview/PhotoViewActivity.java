package com.tuwq.photodraw.photoview;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.tuwq.photodraw.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huang on 2016/12/11.
 */

public class PhotoViewActivity extends Activity {
    @Bind(R.id.photoview)
    ImageView photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoview);
        ButterKnife.bind(this);

        // 监听单击事件
        /*photoview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Toast.makeText(PhotoViewActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });*/
    }
    float scale =0.1f;
    @OnClick(R.id.photoview)
    public void onClick() {
        scale += 0.1f;
        // 获取图片的矩阵 不能修改
        Matrix imageMatrix = photoview.getImageMatrix();
        // 创建矩阵
        Matrix matrix = new Matrix(imageMatrix);
        // 缩放
//        matrix.postScale(scale,scale,photoview.getWidth()/2,photoview.getHeight()/2);

        // 移动
//        matrix.postTranslate(50,50);

        // 旋转
//        matrix.postRotate(scale);

        // 扭曲
        matrix.postSkew(scale,scale);
        // 把新的矩阵设置给图片
        photoview.setImageMatrix(matrix);
    }
}
