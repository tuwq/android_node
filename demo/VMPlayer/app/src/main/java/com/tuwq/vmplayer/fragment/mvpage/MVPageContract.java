package com.tuwq.vmplayer.fragment.mvpage;

import com.tuwq.vmplayer.BasePresenter;
import com.tuwq.vmplayer.BaseView;
import com.tuwq.vmplayer.bean.AreaBean;

import java.util.List;

/**
 * Created by wschun on 2016/10/1.
 */

public interface MVPageContract {
    interface Presenter extends BasePresenter {}

    interface View extends BaseView<Presenter> {
        void setData(List<AreaBean> areaBeanArrayList);
        void setError(String msg);

    }
}
