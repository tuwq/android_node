package com.tuwq.asynchttp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String path = "http://192.168.147.3:9001/loginservlet/login";

    EditText et_username;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = this.findViewById(R.id.et_username);
        et_password = this.findViewById(R.id.et_password);
    }

    public void getBtn(View v) {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = path + "?username="+ URLEncoder.encode(username, "utf-8")+"&password=" + URLEncoder.encode(password, "utf-8");
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        try {
                            String result = new String(responseBody, "utf-8");
                            showToast(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postBtn(View v) {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            Map map = new HashMap();
            map.put("username", username);
            map.put("password", password);
            RequestParams params = new RequestParams(map);
            client.post(path, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        try {
                            String result = new String(responseBody, "utf-8");
                            showToast(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {

        }
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
