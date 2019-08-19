package com.tuwq.vmplayer.fragment.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuwq.vmplayer.R;
import com.tuwq.vmplayer.adapter.HomePageAdapter;
import com.tuwq.vmplayer.bean.VideoBean;
import com.tuwq.vmplayer.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/9/28.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {
    @Bind(R.id.rv_home)
    RecyclerView rvHome;
    @Bind(R.id.srf_fresh)
    SwipeRefreshLayout srfFresh;
    private List<VideoBean> videoBeanlist;
    private HomePageAdapter homePageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, rootView);
            videoBeanlist = new ArrayList<>();
            obserView(540, 640);
            initView();
            new HomePresenter(this);
            presenter.getData(offset, SIZE);
        }
        return rootView;
    }

    private void initView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        homePageAdapter = new HomePageAdapter(videoBeanlist, getActivity(), screenHeight, screenWidth);
        rvHome.setAdapter(homePageAdapter);
        showLoging();
        srfFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                showLoging();
                presenter.getData(0, SIZE);
            }
        });

        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((lastVisibleItemPosition == (homePageAdapter.getItemCount() - 1)) && hasmore) {

                    presenter.getData(offset, SIZE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setData(List<VideoBean> lists) {
        if (refresh) {
            videoBeanlist.clear();
        }

//        if (lists.size()==0){
//            hasmore=false;
//        }else
//           hasmore=true;
        hasmore = (lists.size() != 0);

        offset += lists.size();
        videoBeanlist.addAll(lists);
        homePageAdapter.notifyDataSetChanged();
        dismiss();
        if (srfFresh != null)
            srfFresh.setRefreshing(false);

    }

    @Override
    public void setError(String msg) {
        dismiss();
    }

    private HomeContract.Presenter presenter;

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
