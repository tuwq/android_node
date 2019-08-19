package com.tuwq.mobileplayer.fragment.yuedanpage;

import com.tuwq.mobileplayer.bean.YueDanBean;

import java.util.List;

public interface YueDanMvp {

    interface Presenter{
        void loadData(int offset, int size);
    }

    interface View{
        void setData(List<YueDanBean.PlayListsBean> playLists);
        void setError(int code, Exception e);
    }

}
