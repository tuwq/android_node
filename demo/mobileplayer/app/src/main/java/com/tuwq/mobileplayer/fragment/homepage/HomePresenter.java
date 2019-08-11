package com.tuwq.mobileplayer.fragment.homepage;

import com.tuwq.mobileplayer.URLProviderUtil;
import com.tuwq.mobileplayer.bean.VideoBean;
import com.tuwq.mobileplayer.http.BaseCallBack;
import com.tuwq.mobileplayer.http.HttpManager;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.List;

public class HomePresenter implements HomeMvp.Presenter {
    private static final String TAG = "HomePresenter";

    HomeMvp.View view;
    public HomePresenter(HomeMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int offset, int size) {
        LogUtils.e(TAG,"HomePresenter.loadData,开始加载数据");

        String url = URLProviderUtil.getMainPageUrl(offset,size);

        HttpManager.getInstance().get(url, new BaseCallBack<List<VideoBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code, e);
            }
            @Override
            public void onSuccess(List<VideoBean> videoBeen) {
                LogUtils.e(TAG,"HomePresenter.onSuccess,成功获取到数据");
                view.setData(videoBeen);
            }
        });
    }
}
