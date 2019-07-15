package com.tuwq.mobilesafe;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tuwq.mobilesafe.bean.AppInfo;
import com.tuwq.mobilesafe.engine.AppEngine;


public class ClearCacheActivity extends Activity {

    private MyAsyncTask myAsyncTask;
    private ImageView mLine;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        initView();
        initData();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mLine = (ImageView) findViewById(R.id.clearcache_iv_line);
    }

    /**
     * 加载数据，显示数据
     */
    private void initData() {
        pm = getPackageManager();
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    //查询系统中所有的应用程序的信息，并展示
    private class MyAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            //1.实现线的平移动画
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_clearcache_line);//加载一个xml文件设置的动画
            mLine.startAnimation(animation);

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //2.获取系统中安装应用程序的信息，并且实现获取一个展示一个的效果
            List<AppInfo> allAppInfos = AppEngine.getAllAppInfos(getApplicationContext());
            //遍历集合
            for (AppInfo appInfo : allAppInfos) {
                //获取每个应用程序的信息，并展示
                //2.1.反射getPackageSizeInfo方法根据应用程序的包名，获取应用程序的缓存大小
                //参数1：应用程序的包名
                //参数2：应用程序缓存信息存放的aidl文件
                //pm.getPackageSizeInfo(appInfo.packageName, mStatsObserver);
                //因为getPackageSizeInfo系统隐藏了所以需要反射进行操作
                /*try {
                    Method method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
                    method.invoke(pm, appInfo.packageName,mStatsObserver);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /*IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
        public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {
            //获取缓存大小操作
            long cachesize = stats.cacheSize;//获取应用程序的缓存大小
            String packageName = stats.packageName;//获取应用程序的包名

            System.out.println(packageName+":"+cachesize);
        }
    };*/
}
