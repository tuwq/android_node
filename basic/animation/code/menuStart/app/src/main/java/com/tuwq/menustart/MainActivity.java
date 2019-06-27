package com.tuwq.menustart;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // MenuItem就是被点击的对象
        // 可以获取菜单项的id 通过id判断哪个条目被点击了
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "最下被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings1:
                Toast.makeText(this, "中间被点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings2:
                Toast.makeText(this, "最上被点击", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        // 当用户点击菜单按钮就会执行这个方法
        // 如果返回值是true,那么就会走系统默认的实现
        // 如果想实现自定义菜单效果,可以在这个方法中处理,并且让这个方法返回false
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("这是一个菜单");
        builder.setMessage("菜单内容");
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "菜单确定", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        return false;
    }
}
