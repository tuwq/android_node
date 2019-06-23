package com.tuwq.handlersecondusage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_text;
    private int count = 60;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            countDown();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_text = this.findViewById(R.id.tv_text);
        Message msg = Message.obtain();
        // 发送一条延迟执行的消息
        handler.sendMessageDelayed(msg, 1000);
        // handler.sendEmptyMessageDelayed(1, 1000);
        // runOnUiThread必然运行在主线程中
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 这里的代码一定执行在主线程中

            }
        });
    }

    public void countDown() {
        count--;
        tv_text.setText(count+"");
        Message msg = Message.obtain();
        // 发送一条延迟执行的消息
        handler.sendMessageDelayed(msg, 1000);
    }
}
