package com.tuwq.decodebase64;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URLEncodeDemo();
        Base64Demo();
    }

    /** Base64演示 */
    private void Base64Demo() {
        // 1、获取到图片的byte[]数据
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, boas);  // 把图片写到内存流中
        byte[] rawBytes = boas.toByteArray();                   // 拿到内存流中的数据

        // 2、把byte[]数据通过Base64变成String
        String picString = Base64.encodeToString(rawBytes, Base64.DEFAULT);
        System.out.println("picString = " + picString);

        // 3、把图片类型的String传给服务器

        // 4、把String类型的图片数据从服务器获取到

        // 5、把String类型的图片通过Base64变成原始字节码
        rawBytes = Base64.decode(picString, Base64.DEFAULT);

        // 6、把原始字码变回图片
        bitmap = BitmapFactory.decodeByteArray(rawBytes, 0, rawBytes.length);

        // 7、把图片显示到ImageView中
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    /** URLEncode演示 */
    private void URLEncodeDemo() {
        try {
            String url = "http://www.baidu.com?search=哈哈";
            String encodeUrl = URLEncoder.encode(url, "UTF-8"); // 对URL进行编码
            System.out.println("编码之后的URL " + encodeUrl);

            String decodeUrl = URLDecoder.decode(encodeUrl, "UTF-8");// 对URL进行解编码
            System.out.println("解编过码的URL " + decodeUrl);

            decodeUrl = URLDecoder.decode(url, "UTF-8");
            System.out.println("解没有编码的URL " + decodeUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
