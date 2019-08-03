package com.tuwq.googleplay95.http;

public interface Url {
    //服务器的主机定义，
    String SERVER_HOST = "http://127.0.0.1:8090/";

    //图片的url前缀
    String IMG_PREFIX = SERVER_HOST + "image?name=";

    String Home = SERVER_HOST + "home?index=";
}
