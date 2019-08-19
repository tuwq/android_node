package com.tuwq.mobileplayer.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tuwq.mobileplayer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        // 初始化标题
        setSupportActionBar(toolbar);// 将ToolBar设置为标题栏
        getSupportActionBar().setTitle("VMPlayer");// 修改标题文字
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回按钮
        // 初始化 WebView
        String url = getIntent().getStringExtra("url");
        Toast.makeText(this, "url="+url, Toast.LENGTH_SHORT).show();
        webview.loadUrl(url);
        webview.getSettings().setJavaScriptEnabled(true);// 为了显示网页动态效果，需要打开JavaScript开关

        // 获取网页的加载进度
        webview.setWebViewClient(new WebViewClient(){
            @Override
            // 开始加载界面
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            // 界面加载结束
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            // 当网页的加载进度发生变化时被调用
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
