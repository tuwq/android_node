package com.tuwq.googleplay95.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;
import com.tuwq.googleplay95.view.StateLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.rb_star)
    RatingBar rbStar;
    @Bind(R.id.tv_download_num)
    TextView tvDownloadNum;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_size)
    TextView tvSize;
    @Bind(R.id.ll_info)
    LinearLayout llInfo;

    private String packageName;
    private StateLayout stateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.获取intent的数据
        packageName = getIntent().getStringExtra("packageName");

        //setActionbar
        setActionBar();

        //2.创建StateLayout
        stateLayout = new StateLayout(this);
        //将stateLayout作为Activity的View
        setContentView(stateLayout);

        //设置成功的View
        stateLayout.bindSuccessView(getSuccessView());
        //先显示loadingView
        stateLayout.showLoadingView();

        //3.请求数据
        loadData();
    }

    public View getSuccessView() {
        View view = View.inflate(this, R.layout.activity_detail, null);

        ButterKnife.bind(this,view);
        return view;
    }

    private AppInfo appInfo;

    /**
     * 加载数据
     */
    private void loadData() {
        String url = String.format(Url.Detail,packageName);
        HttpHelper.create()
                .get(url, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();

                        appInfo =  GsonUtil.parseJsonToBean(result,AppInfo.class);
                        if(appInfo!=null){
                            //更新UI
                            updateUI();
                        }
                    }
                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    /**
     * 更新UI的代码
     */
    private void updateUI() {
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+appInfo.iconUrl,ivImage, UILOption.options);
        tvName.setText(appInfo.name);
        rbStar.setRating(appInfo.stars);
        tvDownloadNum.setText("下载："+appInfo.downloadNum);
        tvVersion.setText("版本："+appInfo.version);
        tvDate.setText("日期："+appInfo.date);
        tvSize.setText("大小："+ Formatter.formatFileSize(this,appInfo.size));

        //执行掉落动画
        //1.先让llInfo上去
        //添加一个布局完成的监听器
        llInfo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * 当执行完布局之后，回调该方法，因此可以在该方法中获取宽高
             */
            @Override
            public void onGlobalLayout() {
                llInfo.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                llInfo.setTranslationY(-llInfo.getHeight());
                //再通过属性动画移动下来
                ViewCompat.animate(llInfo)
                        .translationY(0)
                        .setDuration(800)
                        .setStartDelay(400)
                        .setInterpolator(new BounceInterpolator())//像球落地一样的感觉
                        .start();
            }
        });
    }

    /**
     * 设置ActionBar
     */
    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_detail));

        //显示home按钮
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
