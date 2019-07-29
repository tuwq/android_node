package com.tuwq.zhbj.net;

/**
 * 请求服务器的路径
 */
public class NetUrl {

    private static String SERVERURL = "http://10.0.2.2:8080/zhbj";
    //新闻中心
    public static final String NEWSCENTERURL=SERVERURL+"/categories.json";
    //组图
    public static final String PHOTOURL=SERVERURL+"/photos/photos_1.json";

}
