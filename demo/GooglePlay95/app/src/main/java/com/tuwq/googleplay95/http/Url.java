package com.tuwq.googleplay95.http;

public interface Url {
    //服务器的主机定义，
    String SERVER_HOST = "http://127.0.0.1:8090/";

    //图片的url前缀
    String IMG_PREFIX = SERVER_HOST + "image?name=";

    String Home = SERVER_HOST + "home?index=";
    String App = SERVER_HOST + "app?index=";
    String Game = SERVER_HOST + "game?index=";
    String Subject = SERVER_HOST + "subject?index=";
    String Recommend = SERVER_HOST + "recommend?index=0";
    String Category = SERVER_HOST + "category?index=0";
    String Hot = SERVER_HOST + "hot?index=0";
    String Detail = SERVER_HOST + "detail?packageName=%s";//占位符的方式
}
