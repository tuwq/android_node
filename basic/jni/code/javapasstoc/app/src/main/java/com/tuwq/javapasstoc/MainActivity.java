package com.tuwq.javapasstoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private JNI jni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jni = new JNI();
    }

    public void passInt(View v) {
        int result = jni.add(3, 4);
        Toast.makeText(this, result+"", Toast.LENGTH_SHORT).show();
    }

    public void passString(View v) {
        String result = jni.sayHelloInC("abcde");
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    public void passArr(View v) {
        int[]arr = new int[]{1,2,3,4,5};
        // 这个方法执行完 直接是在arr数组上修改的元素内存 没有必要再创建对象保留返回值
        jni.arrElementsIncrease(arr);
        for(int i:arr){
            System.out.println("i="+i);
        }
    }
}
