package com.tuwq.locationdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mText;
    private LocationManager locationManager;
    private MyLocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.text);
        //定位操作
        //1.获取位置的管理者
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //2.定位实现
        listener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("没有GPS定位权限");
            return;
        }
        //参数1：定位
        //参数2：最小的定位的间隔时间
        //参数3：最小的定位的间隔距离
        //参数4：定位的回调监听
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }

    /**定位的回调函数**/
    private class MyLocationListener implements LocationListener {
        //当定位位置和定位时间改变的时候调用
        //location : 定位位置
        @Override
        public void onLocationChanged(Location location) {
            location.getSpeed();//获取定位的速度
            location.getTime();//定位的时间

            double latitude = location.getLatitude();//获取定位的维度
            double longitude = location.getLongitude();//获取定位的经度

            mText.setText("经度："+longitude+"  维度："+latitude);
        }
        //定位状态改变的时候调用的方法
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        //定位方法可用调用的方法
        @Override
        public void onProviderEnabled(String provider) {

        }
        //定位方式不可用调用的方法
        @Override
        public void onProviderDisabled(String provider) {

        }

    }

    @Override
    protected void onDestroy() {
        //3.当activity退出的时候，取消定位操作
        locationManager.removeUpdates(listener);//取消定位，并不会将手机中的GPS关闭，android高版本中不允许程序员通过代码开启和关闭GPS
        super.onDestroy();
    }
}
