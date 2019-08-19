package com.tuwq.encodersadesaes;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private boolean unEncript = true;
    private TextView textView;
    private String password = "#@*%$^s123434334";
    private String privateKey;
    private String publicKey;
    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        try {
            Map<String, Object> keyPair = RSACrypt.genKeyPair();    // 产生密钥对
            privateKey = RSACrypt.getPrivateKey(keyPair);           // 获取密钥对中的私钥
            publicKey = RSACrypt.getPublicKey(keyPair);           // 获取密钥对中的公钥
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * des加密/解密演示
     */
    public void des(View v) {
        try {
            String text = textView.getText().toString();
            if (unEncript) {
                // 加密
                String encryptText = Des.encrypt(text, password);
                textView.setText(encryptText);
            } else {
                // 解密
                String decryptText = Des.decrypt(text, password);
                textView.setText(decryptText);
            }
            unEncript = !unEncript;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * aes加密/解密演示
     */
    public void aes(View v) {
        try {
            String text = textView.getText().toString();
            if (unEncript) {
                // 加密
                String encryptText = Aes.encrypt(text, password);
                textView.setText(encryptText);
            } else {
                // 解密
                String decryptText = Aes.decrypt(text, password);
                textView.setText(decryptText);
            }
            unEncript = !unEncript;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * rsa加密/解密演示
     */
    public void rsa(View v) {
        String text = textView.getText().toString();
        try {
            if (unEncript) {
                // 加密
                byte[] rawBytes = text.getBytes();
                byte[] encriptBytes = RSACrypt.encryptByPrivateKey(rawBytes, privateKey);   // 加密
                String encriptText = RSACrypt.encode(encriptBytes); // 把加密后字节码变成String
                textView.setText(encriptText);
            } else {
                // 解密
                byte[] encriptBytes = RSACrypt.decode(text);    // 获取到加密的字节码
                byte[] rawBytes = RSACrypt.decryptByPublicKey(encriptBytes, publicKey); // 解密
                String string = new String(rawBytes);
                textView.setText(string);
            }
            unEncript = !unEncript;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String jiaoTiao = "因为xxxx原因，向小红借10000块钱，2016年12月31日还";

    /**
     * rsa签名演示
     */
    public void sign(View v) {
        try {
            // 签名，必须使用私钥
            sign = RSACrypt.sign(jiaoTiao.getBytes(), privateKey);
            Toast.makeText(this, "签名完成", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * rsa验证签名演示
     */
    public void verify(View v) {
        try {
            boolean result = RSACrypt.verify("因为xxxx原因，向小红借10000块钱，2016年12月31日还".getBytes(), publicKey, sign);
            Toast.makeText(this, result ? "签名没问题，一会给你拿钱" : "你的借条有问题，不能借钱给你", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}