package com.tuwq.payweixin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
    }

    public void weixinPay(View v) {
        // 把购物车的内容(要购买的商品价格、数量、颜色、收货地址、手机号、用户账号)提交给服务器
        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    wechatPay(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: 请求网络失败", error);
            }
        };
        StringRequest request = new StringRequest(url, listener, errorListener);
        Volley.newRequestQueue(this).add(request);
    }

    /** 把订单信息提交给微信的支付插件进行支付 */
    private void wechatPay(String orderInfo) throws Exception {
        Log.i(TAG, "wechatPay: " + orderInfo);

        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);

        // 判断是否支付微信支付
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            Toast.makeText(this, "您没有安装微信，或者微信版本太低", Toast.LENGTH_SHORT).show();
            return;
        }

        // 把订单信息提交给微信的支付插件进行支付
        JSONObject json = new JSONObject(orderInfo);
        if(null != json && !json.has("retcode") ){
            PayReq req = new PayReq();
            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
            req.appId			= json.getString("appid");
            req.partnerId		= json.getString("partnerid");
            req.prepayId		= json.getString("prepayid");
            req.nonceStr		= json.getString("noncestr");
            req.timeStamp		= json.getString("timestamp");
            req.packageValue	= json.getString("package");
            req.sign			= json.getString("sign");
            req.extData			= "app data"; // optional
            Toast.makeText(this, "正常调起支付", Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        }else{
            Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
            Toast.makeText(this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
        }
    }

}

