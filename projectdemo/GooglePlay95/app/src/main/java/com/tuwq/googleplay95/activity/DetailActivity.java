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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.module.DetailDesModule;
import com.tuwq.googleplay95.module.DetailDownloadModule;
import com.tuwq.googleplay95.module.DetailInfoModule;
import com.tuwq.googleplay95.module.DetailSafeModule;
import com.tuwq.googleplay95.module.DetailScreenModule;
import com.tuwq.googleplay95.util.GsonUtil;
import com.tuwq.googleplay95.view.StateLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.ll_container)
    LinearLayout llContainer;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.fl_download)
    FrameLayout fl_download;

    private String packageName;
    private StateLayout stateLayout;

    private DetailInfoModule infoModule;
    private DetailSafeModule safeModule;
    private DetailScreenModule screenModule;
    private DetailDesModule desModule;
    private DetailDownloadModule downloadModule;

    private AppInfo appInfo;
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

    /**
     * 获取成功的VIEW
     *
     * @return
     */
    public View getSuccessView() {
        View view = View.inflate(this, R.layout.activity_detail, null);
        ButterKnife.bind(this, view);

        //1.加入info 模块
        infoModule = new DetailInfoModule();
        llContainer.addView(infoModule.getModuleView());
        //2.加入safe 模块
        safeModule = new DetailSafeModule();
        llContainer.addView(safeModule.getModuleView());
        //3.加入screen 模块
        screenModule = new DetailScreenModule();
        screenModule.setActivity(DetailActivity.this);
        llContainer.addView(screenModule.getModuleView());
        //4.加入des 模块
        desModule = new DetailDesModule();
        llContainer.addView(desModule.getModuleView());
        desModule.setScrollView(scrollView);

        //5.加入download 模块
        downloadModule = new DetailDownloadModule();
        fl_download.addView(downloadModule.getModuleView());

        return view;
    }


    /**
     * 加载数据
     */
    private void loadData() {
        String url = String.format(Url.Detail, packageName);
        HttpHelper.create()
                .get(url, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();

                        appInfo = GsonUtil.parseJsonToBean(result, AppInfo.class);
                        if (appInfo != null) {
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
        //1.绑定info 模块的数据
        infoModule.bindData(appInfo);

        //2.绑定safe 模块的数据
        safeModule.bindData(appInfo);

        //3.绑定screen 模块的数据
        screenModule.bindData(appInfo);

        //4.绑定des 模块的数据
        desModule.bindData(appInfo);

        //5.绑定download 模块的数据
        downloadModule.bindData(appInfo);
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
    protected void onDestroy() {
        super.onDestroy();
        //避免内存泄露
        downloadModule.removeObserver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
