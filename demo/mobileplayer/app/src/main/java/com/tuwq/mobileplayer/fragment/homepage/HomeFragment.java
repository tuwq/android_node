package com.tuwq.mobileplayer.fragment.homepage;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tuwq.mobileplayer.BaseFragment;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.bean.VideoBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.List;

import butterknife.Bind;

public class HomeFragment extends BaseFragment implements HomeMvp.View {

    private static final String TAG = "HomeFragment";

    @Bind(R.id.recylerview)
    RecyclerView recylerview;

    private HomeMvp.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 创建view,填充数据
     */
    @Override
    protected void initView() {
        // TextView tv_test = (TextView) rootView.findViewById(R.id.tv_test);
        // 获取初始化参数
        // tv_test.setText("这是 首页 的界面");
        // 创建 presenter
        LogUtils.e(TAG, "HomeFragment.initView,创建 presenter,并请求数据");
        presenter = new HomePresenter(this);
        presenter.loadData(0, 10);

        // 填充 RecylerView 列表
    }


    /**
     * 获得到了数据
     * @param videoBeen 数据
     */
    @Override
    public void setData(List<VideoBean> videoBeen) {
        LogUtils.e(TAG, "HomeFragment.setData,videoBeen=" + videoBeen.size());
    }

    @Override
    public void onError(int code, Exception e) {

    }
}
