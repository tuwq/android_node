package com.tuwq.mobileplayer.fragment.mvpage;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tuwq.mobileplayer.BaseFragment;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.adapter.MvPageAdapter;
import com.tuwq.mobileplayer.bean.AreaBean;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MvFragment extends BaseFragment implements MvMvp.View{
    private static final String TAG = "MvFragment";

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    private MvMvp.Presener presener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mv;
    }

    @Override
    protected void initView() {
        presener = new MvPresenter(this);
        presener.loadData();
    }

    @Override
    public void setData(List<AreaBean> areaBeen) {
        LogUtils.e(TAG,"MvFragment.setData,areaBeen="+areaBeen.size());
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        for (AreaBean areaBean : areaBeen) {
            fragmentList.add(MvChildFragment.newInstance(areaBean.getCode()));
            titleList.add(areaBean.getName());
        }
        // MvPageAdapter mvPageAdapter = new MvPageAdapter(this.getFragmentManager(), fragmentList);
        MvPageAdapter mvPageAdapter = new MvPageAdapter(this.getFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(mvPageAdapter);
        // 关联 ViewPager
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onError(int code, Exception e) {
        LogUtils.e(TAG, "加载数据失败");
    }
}
