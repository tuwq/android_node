package com.tuwq.vmplayer.fragment.mvpage;

import com.tuwq.vmplayer.BasePresenter;
import com.tuwq.vmplayer.BaseView;
import com.tuwq.vmplayer.bean.VideoBean;

import java.util.List;

/**
 * Created by wschun on 2016/10/1.
 */

public interface MVPageitemContract {
    interface Presenter extends BasePresenter {

      void   getData(int moffest, int size, String areaCode);
    }

    interface View extends BaseView<Presenter> {

        void setData(List<VideoBean> videoBeanList);
        void setError(String msg);

    }
}
