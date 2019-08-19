package com.tuwq.volleydemo;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class GsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;
    private Class<T> beanClass;

    public GsonRequest(int method, String url, Class<T> beanClass, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.beanClass = beanClass;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> beanClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, beanClass, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }
        T javaBean = new Gson().fromJson(json, beanClass);
        return Response.success(javaBean, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
