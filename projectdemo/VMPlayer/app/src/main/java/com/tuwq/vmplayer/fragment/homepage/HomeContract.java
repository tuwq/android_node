package com.tuwq.vmplayer.fragment.homepage;

import com.tuwq.vmplayer.BasePresenter;
import com.tuwq.vmplayer.BaseView;
import com.tuwq.vmplayer.bean.VideoBean;

import java.util.List;

/**
 * Created by wschun on 2016/9/30.
 */

public interface HomeContract {


    interface  Presenter extends BasePresenter{


    }

    interface View extends BaseView<Presenter>{
        void setData(List<VideoBean> lists);
        void setError(String msg);
    }

}
