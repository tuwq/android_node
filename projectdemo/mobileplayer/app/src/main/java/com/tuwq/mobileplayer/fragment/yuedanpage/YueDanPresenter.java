package com.tuwq.mobileplayer.fragment.yuedanpage;

import com.tuwq.mobileplayer.URLProviderUtil;
import com.tuwq.mobileplayer.bean.YueDanBean;
import com.tuwq.mobileplayer.http.BaseCallBack;
import com.tuwq.mobileplayer.http.HttpManager;
import com.tuwq.mobileplayer.utils.LogUtils;

public class YueDanPresenter implements YueDanMvp.Presenter {

    private static final String TAG = "YueDanPresenter";

    YueDanMvp.View view;

    public YueDanPresenter(YueDanMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int offset, int size) {
        String url = URLProviderUtil.getMainPageYueDanUrl(offset,size);
        LogUtils.e(TAG,"YueDanPresenter.loadData,url="+url);

        HttpManager.getInstance().get(url, new BaseCallBack<YueDanBean>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.setError(code,e);
            }

            @Override
            public void onSuccess(YueDanBean yueDanBean) {
                view.setData(yueDanBean.getPlayLists());
            }
        });
    }
}
