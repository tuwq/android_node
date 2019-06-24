package com.tuwq.androidmultithreaddownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_url;
    EditText et_count;
    Button btn_download;
    LinearLayout ll_progress;

    private static String path = "http://blog.img.tuwq.cn/upload/user/avatar/11E68E08859F3D3ED8123CA35AB08B6F.jpg";
    int threadCount;
    int blockSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = this.findViewById(R.id.et_url);
        et_count = this.findViewById(R.id.et_count);
        btn_download = this.findViewById(R.id.btn_download);
        ll_progress = this.findViewById(R.id.ll_progress);

        btn_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 给线性布局中添加进度条
        ll_progress.removeAllViews();
        String temp = et_count.getText().toString().trim();
        int count = Integer.parseInt(temp);
        for (int i = 0; i < count; i++) {
            // 获取应用的上下文
            View.inflate(getApplicationContext(), R.layout.item, ll_progress);

        }
        threadCount = count;
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        int contentLength = connection.getContentLength();
                        // 在本地创建一个一样大的文件
                        RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
                        file.setLength(contentLength);
                        blockSize = contentLength/threadCount;
                        // 计算线程的下载范围
                        for (int i = 0; i < threadCount; i++) {
                            int startIndex = i * blockSize;
                            int endIndex = (i + 1) * blockSize - 1;
                            if (i == threadCount - 1) {
                                // 说明是最后一个线程
                                endIndex = contentLength - 1;
                            }
                            // 设置进度条
                            ProgressBar pb = (ProgressBar)ll_progress.getChildAt(i);
                            pb.setMax(endIndex - startIndex);
                            new DonwloadThread(startIndex, endIndex, i).start();
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class DonwloadThread extends Thread {
        private int startIndex;
        private int endIndex;
        private int threadId;
        private ProgressBar pb;

        public DonwloadThread(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
            this.pb = (ProgressBar)ll_progress.getChildAt(threadId);
        }

        @Override
        public void run() {
            try {
                // 读取出记录下来的位置
                File temp = new File(getFileName(path) + "_" + threadId + ".log");
                if (temp != null && temp.length() > 0) {
                    // 说明日志文件有内容
                    FileInputStream fis = new FileInputStream(temp);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    String result = reader.readLine();
                    // 读取记录下来的位置更新下载请求数据的起始位置
                    startIndex = Integer.parseInt(result);
                    fis.close();
                }

                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                // 设置Range头,用计算好的开始索引和结束索引到服务端请求数据
                connection.setRequestProperty("Range", "bytes="+startIndex+"-" + endIndex);
                int responseCode = connection.getResponseCode();
                if (responseCode == 206) {
                    System.out.println("线程" + threadId + "开始下载" + startIndex);
                    InputStream inputStream = connection.getInputStream();
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
                    // seek到不同的startIndex位置写入数据
                    file.seek(startIndex);
                    int saveCount = 0;
                    while((len = inputStream.read(buffer))!=-1) {
                        file.write(buffer, 0, len);
                        // 记录下载位置
                        saveCount += len;
                        int postion = saveCount + startIndex;
                        // 设置进度条下载位置
                        pb.setProgress(postion - threadId * blockSize);

                        // 存储下载位置,rwd表明该文件立刻存储,不会进行缓存
                        RandomAccessFile tempFile = new RandomAccessFile(getFileName(path)+"_"+threadId+".log", "rwd");
                        tempFile.write(String.valueOf(postion).getBytes());
                        tempFile.close();
                    };
                    file.close();
                    System.out.println("线程" + threadId + "下载结束" + endIndex);
                    // 删除对应的日志文件
                    if (temp != null) {
                        temp.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName(String path) {
        String[] result = path.split("/");
        return getCacheDir().getAbsolutePath()+"/"+result[result.length - 1];
    }
}
