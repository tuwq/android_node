package com.tuwq.mobileplayer.fragment.mvpage;

import com.tuwq.mobileplayer.bean.AreaBean;

import java.util.List;

public interface MvMvp {

    interface Presener{
        void loadData();
    }

    interface View{
        void setData(List<AreaBean> areaBeen);
        void onError(int code, Exception e);
    }
}
