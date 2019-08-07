package com.tuwq.opennetlib.retrofit;

import com.tuwq.opennetlib.bean.HelloBean;
import com.tuwq.opennetlib.bean.LoginBean;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by huang on 2016/12/10.
 */

public interface HeimaService {
    // 定义访问服务器的方法，每一个方法需要对应一个url
    @GET("get") // 内部把这个地址拼接成完整的地址 http://192.168.78.22:8080/webapi/get?name=&&pwd=
    Call<ResponseBody> hello(@Query("name") String name,@Query("pwd") String pwd);// Call<ResponseBody>  ResponseBody指服务器返回数据类型

    @GET("get") // 内部把这个地址拼接成完整的地址 http://192.168.78.22:8080/webapi/get?name=&&pwd=
    Call<HelloBean> hello2(@Query("name") String name, @Query("pwd") String pwd);// Call<ResponseBody>  ResponseBody指服务器返回数据类型

    @FormUrlEncoded// post请求需要对内容进行编码
    @POST("post")// http://192.168.78.22:8080/webapi/post
    Call<ResponseBody> login(@Field("user") String user,@Field("pwd") String pwd);

    @POST("post")
    Call<ResponseBody> login2(@Body LoginBean bean);

    @Multipart// 上传文件
    @POST("upload") // http://192.168.78.22:8080/webapi/upload
    Call<ResponseBody> upload(@Part MultipartBody.Part body);

    @GET("upload/b.jpg")// http://192.168.78.22:8080/webapi/upload/b.jpg
    Call<ResponseBody> download();

    @GET()// http://192.168.78.22:8080/webapi/upload/b.jpg
    Call<ResponseBody> download(@Url String url);// http://192.168.78.22:8080/webapi/+url
}

