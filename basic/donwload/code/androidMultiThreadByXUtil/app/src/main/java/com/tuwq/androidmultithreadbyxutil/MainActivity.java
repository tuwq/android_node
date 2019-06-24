package com.tuwq.androidmultithreadbyxutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText et_url;
    private ProgressBar pb;
    private Button btn_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = (EditText) findViewById(R.id.et_url);
        pb = (ProgressBar) findViewById(R.id.pb_progress);
        btn_down = (Button) findViewById(R.id.btn_download);
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils utils = new HttpUtils();
                String url = "http://blog.img.tuwq.cn/upload/user/avatar/11E68E08859F3D3ED8123CA35AB08B6F.jpg";
                utils.download(url, getFileName(url), true, new RequestCallBack<File>() {
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {

                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        pb.setMax((int)total);
                        pb.setProgress((int)current);
                    }
                });
            }
        });
    }

    private String getFileName(String path) {
        String[] result = path.split("/");
        return getCacheDir().getAbsolutePath()+"/"+result[result.length-1];
    }
}
