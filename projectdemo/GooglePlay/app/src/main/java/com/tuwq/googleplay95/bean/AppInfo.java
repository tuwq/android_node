package com.tuwq.googleplay95.bean;

import java.util.ArrayList;

public class AppInfo {
    public String des;
    public String downloadUrl;
    public String iconUrl;
    public int id;
    public String name;
    public String packageName;
    public long size;
    public float stars;

    //添加新字段
    public String author;//app的作者，一般是公司名字
    public String date;//上传日期
    public String downloadNum;//下载数量
    public String version;
    public ArrayList<String> screen;//截图的url
    public ArrayList<SafeInfo> safe;//安全信息
}
