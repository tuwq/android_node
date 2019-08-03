package com.tuwq.googleplay95.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class HomeFragment extends BaseFragment {

    public View getSuccessView() {
        TextView textView = new TextView(getActivity());
        textView.setTextSize(25);
        textView.setText(this.getClass().getSimpleName());
        return textView;
    }

    public void loadData() {
        //加载数据的代码
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        //模拟请求数据成功，那么应该显示successView
                        stateLayout.showSuccessView();
                    }
                }, 2000);
    }
}
