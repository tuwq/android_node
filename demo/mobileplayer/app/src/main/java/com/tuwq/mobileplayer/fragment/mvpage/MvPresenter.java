package com.tuwq.mobileplayer.fragment.mvpage;

import com.tuwq.mobileplayer.URLProviderUtil;
import com.tuwq.mobileplayer.bean.AreaBean;
import com.tuwq.mobileplayer.http.BaseCallBack;
import com.tuwq.mobileplayer.http.HttpManager;

import java.util.List;

public class MvPresenter implements MvMvp.Presener {

    MvMvp.View view;

    public MvPresenter(MvMvp.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        String url = URLProviderUtil.getMVareaUrl();

        HttpManager.getInstance().get(url, new BaseCallBack<List<AreaBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                view.onError(code,e);
            }

            @Override
            public void onSuccess(List<AreaBean> areaBeen) {
                view.setData(areaBeen);
            }
        });
    }
}
