package com.tuwq.opennetlib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostStringRequest extends StringRequest {
    public PostStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public PostStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
    private Map<String,String> map = new HashMap<>();
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
    public void addParam(String key, String value){
        map.put(key,value);
    }
}
