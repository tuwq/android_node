package com.tuwq.vmplayer.fragment.yuedanpage;

import com.tuwq.vmplayer.BasePresenter;
import com.tuwq.vmplayer.BaseView;
import com.tuwq.vmplayer.bean.YueDanBean;

import java.util.List;

/**
 * Created by Mr.Wang
 * Date  2016/9/5.
 * Email 1198190260@qq.com
 */
public interface YueDanFragmentContract {

    interface Presenter extends BasePresenter {}

    interface View extends BaseView<Presenter> {
        void setData(List<YueDanBean.PlayListsBean> data);
        void setError(String error);
    }

}
