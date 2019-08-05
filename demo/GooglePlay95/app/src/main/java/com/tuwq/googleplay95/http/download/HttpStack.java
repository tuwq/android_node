package com.tuwq.googleplay95.http.download;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * 负责封装http请求，提供get和post请求方法 Http请求的实现方案： android原生：HttpURLConnection,
 * HttpClient 第三方类库：XUtil, OKHttp, Volley, async-http, Retrofit等；
 * @author lxj
 */
public class HttpStack {
    private String tag = HttpStack.class.getSimpleName();

    private static HttpStack mInstance = new HttpStack();

    private HttpStack() {
    }

    public static HttpStack getInstance() {
        return mInstance;
    }

    /**
     * 下载文件，返回流对象
     * @param url
     * @return
     */
    public HttpResult download(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                return new HttpResult(httpClient, httpGet, httpResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Http返回结果的进一步封装
     * @author Administrator
     *
     */
    public static class HttpResult {
        private HttpClient httpClient;
        private HttpGet httpGet;
        private HttpResponse httpResponse;
        private InputStream inputStream;

        public HttpResult(HttpClient httpClient, HttpGet httpGet,
                          HttpResponse httpResponse) {
            super();
            this.httpClient = httpClient;
            this.httpGet = httpGet;
            this.httpResponse = httpResponse;
        }

        /**
         * 获取状态码
         *
         * @return
         */
        public int getStatusCode() {
            StatusLine status = httpResponse.getStatusLine();
            return status.getStatusCode();
        }

        /**
         * 获取输入流
         *
         * @return
         */
        public InputStream getInputStream() {
            if (inputStream == null && getStatusCode() < 300) {
                HttpEntity entity = httpResponse.getEntity();
                try {
                    inputStream = entity.getContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return inputStream;
        }

        /**
         * 关闭链接和流对象
         */
        public void close() {
            if (httpGet != null) {
                httpGet.abort();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭链接
            if (httpClient != null) {
                httpClient.getConnectionManager().closeExpiredConnections();
            }
        }
    }

}

