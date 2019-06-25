package com.tuwq.alertdialog;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private boolean[] checkedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    /**
     * 普通对话框,确定取消
     * @param v
     */
    public void normal(View v) {
        // 创建对话框,这里的上下文要传入activity,而不是applicationContext
        AlertDialog.Builder builder = new Builder(this);
        // 设置标题
        builder.setTitle(R.string.dialog_title);
        // 显示的具体内容
        builder.setMessage("我是内容,关于最近xxxx");
        // 确定按钮
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "已确定", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "已取消", Toast.LENGTH_SHORT).show();
            }
        });
        // 显示按钮
        builder.show();
    }

    /**
     * 单选对话框
     * @param v
     */
    public void singleChoice(View v) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("请选择你喜欢的编程语言");
        // 等待选择的选项
        final String[] items = {"C","C++","Java","JavaScript","Go"};
        // 默认选JavaScript
        builder.setSingleChoiceItems(items, 4, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
                // 关闭对话框
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 多选对话框
     * @param v
     */
    public void multiChoice(View v) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("请选择你喜欢的水果");
        // 等待选择的选项
        final String[] items = {"西瓜","芒果","香蕉","榴莲","苹果","荔枝"};
        checkedItems = new boolean[]{true,false,false,true,false,true};
        builder.setMultiChoiceItems(items, checkedItems, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// which 那个条目被选中的索引
                // isCheck 被点击之后的条目状态
                // Toast.makeText(getApplicationContext(), items[which]+(isChecked?"选中":"未选中"), Toast.LENGTH_SHORT).show();
                checkedItems[which]= isChecked;
                for(int i = 0;i<items.length;i++){
                    if(checkedItems[i])
                        System.out.println("添加"+items[i]);
                }
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 进度条按钮
     * @param v
     */
    public void progress(View v) {
        final ProgressDialog dialog = new ProgressDialog(this);
        // 设置当前的进度条对话框样式为水平方向
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("正在玩命加载....");
        // 满值
        dialog.setMax(100);
        // 显示进度条
        dialog.show();
        new Thread(){
            public void run() {
                for(int i = 0;i<=100;i++){
                    // 渐渐提高进度
                    dialog.setProgress(i);
                    SystemClock.sleep(100);
                }
                // 让当前对话框消失
                dialog.dismiss();
            };
        }.start();

    }
}
