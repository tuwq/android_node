package com.tuwq.mobileplayer.fragment.mvpage;

import com.tuwq.mobileplayer.bean.VideoBean;

import java.util.List;

public interface MvChildMvp {

    interface Presenter{
        void loadData(String area, int offset, int size);
    }

    interface View{
        void setData(List<VideoBean> videos);
        void onError(int code, Exception e);
    }

}
