package com.tuwq.vmplayer.fragment.homepage;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tuwq.vmplayer.bean.VideoBean;
import com.tuwq.vmplayer.http.OkHttpManager;
import com.tuwq.vmplayer.http.StringCallBack;
import com.tuwq.vmplayer.util.URLProviderUtil;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;

/**
 * Created by wschun on 2016/9/30.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getData(int offest, int size) {
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMainPageUrl(offest, size), null, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                System.out.println("====================================================================");
                System.out.println("====================================================================");

                view.setError(e.getLocalizedMessage());
            }
            @Override
            public void onResponse(String response) {
                ArrayList<VideoBean> fristPageBeanList=new ArrayList<VideoBean>();
                JsonParser parser=new JsonParser();
                Log.e("response",response);
                JsonElement jsonElement;
                try {
                    jsonElement = parser.parse(response);
                } catch (Exception e) {
                    VideoBean videoBean = new VideoBean();
                    fristPageBeanList.add(videoBean);
                    view.setData(fristPageBeanList);
                    return;
                }
                JsonArray asJsonArray = jsonElement.getAsJsonArray();
                Iterator iterator = asJsonArray.iterator();
                if (iterator.hasNext()){
                    while (iterator.hasNext()){
                        JsonElement je = (JsonElement) iterator.next();
                        VideoBean videoBean = new Gson().fromJson(je, VideoBean.class);
                        fristPageBeanList.add(videoBean);
                    }
                }
                view.setData(fristPageBeanList);
            }
        });
    }


}
