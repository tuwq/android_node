package com.tuwq.zhbj;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsDetailActivity extends Activity implements View.OnClickListener {

    private String mUrl;

    @ViewInject(R.id.titlebar_tv_title)
    private TextView mTitle;

    @ViewInject(R.id.titlebar_btn_menu)
    private ImageButton mMenu;

    @ViewInject(R.id.titlebar_btn_back)
    private ImageButton mBack;

    @ViewInject(R.id.titlebar_ll_shareandtextsize)
    private LinearLayout mTextSizeAndShare;

    @ViewInject(R.id.titlebar_btn_textsize)
    private ImageButton mTextSize;

    @ViewInject(R.id.titlebar_btn_share)
    private ImageButton mShare;

    @ViewInject(R.id.newsdetail_wb_webview)
    private WebView mWebView;

    @ViewInject(R.id.newsdetail_pb_loading)
    private ProgressBar mLoading;

    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_news_detail);
        // 接受传递过来的url
        mUrl = getIntent().getStringExtra("url");

        ViewUtils.inject(this);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTitle.setText("详情");
        mMenu.setVisibility(View.GONE);
        mBack.setVisibility(View.VISIBLE);
        mTextSizeAndShare.setVisibility(View.VISIBLE);

        // 通过webView显示网页
        mWebView.loadUrl(mUrl);

        // 设置webview操作
        webSettings = mWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);// 显示放大/缩小按钮（wap网页不支持）
        webSettings.setUseWideViewPort(true);// 双击放大/缩小（wap网页不支持）
        webSettings.setJavaScriptEnabled(true);// 是否支持js操作

        // 监听webview加载网页的操作
        mWebView.setWebViewClient(new WebViewClient(){
            // 当网页打开的时候调用的方法
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mLoading.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            // 当网页加载完成的时候调用的方法
            @Override
            public void onPageFinished(WebView view, String url) {
                mLoading.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        mBack.setOnClickListener(this);
        mTextSize.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_btn_back:
                finish();
                break;
            case R.id.titlebar_btn_textsize:
                showSetTextSizeDialog();
                break;
            case R.id.titlebar_btn_share:
                //showShare();
                break;
        }
    }

    private String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
            "超小号字体" };
    private int textSize;
    /** 设置保存选中字体大小的索引 **/
    private int currentTextSize = 2;

    /**
     * 设置字体大小的dialog
     */
    private void showSetTextSizeDialog() {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("字体设置");

        // 设置单选按钮组
        // items : 单选按钮组的文本的数组
        // checkedItem : 默认选中按钮
        // 参数3：选中的点击事件
        builder.setSingleChoiceItems(items, currentTextSize,
                new DialogInterface.OnClickListener() {
                    // which : 选中的按钮的索引
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 当选中按钮的时候，将按钮的索引保存起来
                        textSize = which;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据选中按钮的索引设置字体大小
                switch (textSize) {
                    case 0:
                        webSettings.setTextSize(TextSize.LARGEST);
                        break;
                    case 1:
                        webSettings.setTextSize(TextSize.LARGER);
                        break;
                    case 2:
                        webSettings.setTextSize(TextSize.NORMAL);
                        break;
                    case 3:
                        webSettings.setTextSize(TextSize.SMALLER);
                        break;
                    case 4:
                        webSettings.setTextSize(TextSize.SMALLEST);
                        break;
                }
                currentTextSize = textSize;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
