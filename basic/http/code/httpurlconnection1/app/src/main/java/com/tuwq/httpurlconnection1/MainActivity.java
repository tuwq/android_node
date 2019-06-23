package com.tuwq.httpurlconnection1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private String path = "http://192.168.147.3:9001/loginservlet/login";

    EditText et_username;
    EditText et_password;
    Button btn_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = this.findViewById(R.id.et_username);
        et_password = this.findViewById(R.id.et_password);
        btn_get = this.findViewById(R.id.btn_get);
    }

    public void getBtn(View v) {
        new Thread(){
            @Override
            public void run() {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                // GET方式提交
                try {
                    // 将中文进行编码
                    String tempUrl = path + "?username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
                    URL url = new URL(tempUrl);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream inputStream = connection.getInputStream();
                        String result = Utils.getStringFromStream(inputStream);
                        showToast(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void postBtn(View v) {
        new Thread(){
            public void run() {
                String username = et_username.getText().toString();
                String pwd = et_password.getText().toString();
                try {
                    String params = "username="+URLEncoder.encode(username,"utf-8")+"&password="+URLEncoder.encode(pwd,"utf-8");
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(params.length()));
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(params.getBytes());
                    int code = connection.getResponseCode();
                    if(code==200){
                        InputStream inputStream = connection.getInputStream();
                        String rusult = Utils.getStringFromStream(inputStream);
                        showToast(rusult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    private void showToast(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
