package com.tuwq.mobileplayer.fragment.mvpage;

import com.tuwq.mobileplayer.URLProviderUtil;
import com.tuwq.mobileplayer.bean.MvListBean;
import com.tuwq.mobileplayer.http.BaseCallBack;
import com.tuwq.mobileplayer.http.HttpManager;

public class MvChildPresenter implements MvChildMvp.Presenter {
    private static final String TAG = "MvChildPresenter";

    private MvChildMvp.View view;

    public MvChildPresenter(MvChildMvp.View view) {
        this.view = view;
    }

    public void loadData(String area, int offset, int size) {
        String url = URLProviderUtil.getMVListUrl(area,offset,size);
        HttpManager.getInstance().get(url, new BaseCallBack<MvListBean>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code,e);
            }

            @Override
            public void onSuccess(MvListBean mvListBean) {
                view.setData(mvListBean.getVideos());
            }
        });
    }
}
