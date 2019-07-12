package com.tuwq.mobilesafe.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.tuwq.mobilesafe.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppEngine {
    /**
     * 获取系统中安装所有应用程序的信息
     */
    public static List<AppInfo> getAllAppInfos(Context context){
        List<AppInfo> list = new ArrayList<AppInfo>();
        //1.包的管理者
        PackageManager pm = context.getPackageManager();
        //2.获取安装的所有应用程序的信息
        //flags : 获取额外信息的标示
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        //3.遍历集合，从应用程序的信息中获取出我们需要的信息
        for (PackageInfo packageInfo : installedPackages) {
            //包名
            String packageName = packageInfo.packageName;
            //获取应用程序清单文件中的application的信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //应用程序的名称
            String name = applicationInfo.loadLabel(pm).toString();
            //应用程序的图标
            Drawable icon = applicationInfo.loadIcon(pm);
            //占用空间大小获取
            String sourceDir = applicationInfo.sourceDir;//data\app\应用程序文件.apk
            long size = new File(sourceDir).length();//获取apk文件的大小
            //获取应用程序在系统中的标示（比如是否是系统应用程序的标示，是否安装在sd卡的标示）
            //flags:总标示
            int flags = applicationInfo.flags;
            //判断是否是系统程序
            //ApplicationInfo.FLAG_SYSTEM : 附属标示
            boolean isSystem;
            if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                //系统应用程序
                isSystem = true;
            }else{
                //不是系统应用程序，用户程序
                isSystem = false;
            }
            //判断是否安装在手机内存还是SD卡中
            boolean isSD;
            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
                //安装在SD卡
                isSD = true;
            }else{
                //安装手机内存中
                isSD = false;
            }
            //将信息保存到bean类中
            AppInfo appInfo = new AppInfo(packageName, name, icon, size, isSystem, isSD);
            //将bean类保存到list集合中
            list.add(appInfo);
        }
        return list;
    }
}
