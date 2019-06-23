package com.tuwq.httpclient1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_username;
    EditText et_password;

    String path = "http://192.168.147.3:9001/loginservlet/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = this.findViewById(R.id.et_username);
        et_password = this.findViewById(R.id.et_password);
    }

    public void getBtn(View v) {
        new Thread(){
            @Override
            public void run() {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    String uri = path + "?username=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");
                    HttpGet request = new HttpGet(uri);
                    HttpResponse response = httpClient.execute(request);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        InputStream inputStream = response.getEntity().getContent();
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
            @Override
            public void run() {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                try {
                    BasicNameValuePair pair1 = new BasicNameValuePair("username", username);
                    BasicNameValuePair pair2 = new BasicNameValuePair("password", password);
                    List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
                    pairs.add(pair1);
                    pairs.add(pair2);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,"utf-8");
                    HttpPost httpPost = new HttpPost(path);
                    httpPost.setEntity(entity);
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpResponse response = httpClient.execute(httpPost);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        InputStream inputStream = response.getEntity().getContent();
                        String result = Utils.getStringFromStream(inputStream);
                        showToast(result);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
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
