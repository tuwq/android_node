package com.tuwq.mobileplayer.fragment.homepage;

import com.tuwq.mobileplayer.bean.VideoBean;

import java.util.List;

public interface HomeMvp {
    /** Presenter 层接口,用于加载数据 */
    interface Presenter {
        void loadData(int offset, int size);
    }

    /** view 层接口，用于更新界面的回调 */
    interface View{
        void setData(List<VideoBean> videoBeen);
        void onError(int code, Exception e);
        void showLoading();
        void dismissLoading();
    }
}
