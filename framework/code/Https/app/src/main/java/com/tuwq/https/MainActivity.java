package com.tuwq.https;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //conn1();
                conn2();
            }
        }).start();

    }

    //双向认证
    private void conn2() {
        try {
            // 服务器端需要验证的客户端证书，其实就是客户端的keystore
            KeyStore keyStore = KeyStore.getInstance("BKS");
            //读取证书
            InputStream ksIn = getResources().getAssets().open("client.bks");
            //加载证书
            keyStore.load(ksIn, "123456".toCharArray());
            ksIn.close();
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, "123456".toCharArray());


            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance("BKS");
            InputStream tsIn = getResources().getAssets().open("truststore.bks");
            trustStore.load(tsIn, "123456".toCharArray());
            tsIn.close();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            //初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            //通过HttpsURLConnection设置链接
            URL connectUrl = new URL("https://10.0.2.2:8443");
            HttpsURLConnection conn = (HttpsURLConnection) connectUrl.openConnection();
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
            //设置信任主机ip
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            InputStream inputStream = conn.getInputStream();

            //获取数据
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] b = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(b)) != -1) {
                out.write(b, 0, len);
            }
            String string = out.toString();

            Log.i("hh", string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单向认证
    private void conn1() {
        try {
            //获取公钥信息
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(algorithm);
            //删除默认的公钥
            keyStore.load(null);
            String alias = "heima";
            //设置公钥的类型
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            //获取公钥资源
            InputStream tsIn = getResources().getAssets().open("heima.crt");
            //获取公钥对象
            Certificate cert = factory.generateCertificate(tsIn);
            //设置自己的公钥
            keyStore.setCertificateEntry(alias, cert);
            //初始化公钥
            tmf.init(keyStore);

            //初始化SSLContext
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            //通过HttpsURLConnection设置链接
            URL url = new URL("https://10.0.2.2:8443");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
                    .openConnection();
            httpsURLConnection.setSSLSocketFactory(context.getSocketFactory());
            /***********************************************************************/
            //设置信任主机ip
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {

                    return true;
                }
            });
            /***********************************************************************/
            //获取服务器反馈的数据
            InputStream in = httpsURLConnection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] b = new byte[1024];
            int len = -1;
            while((len = in.read(b)) != -1){
                outputStream.write(b, 0, len);
            }
            String string = outputStream.toString();

            Log.i("MainActivity", string);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
