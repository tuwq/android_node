package com.tuwq.googleplay95.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.DimenUtil;
import com.tuwq.googleplay95.util.DrawableUtil;
import com.tuwq.googleplay95.util.GsonUtil;
import com.tuwq.googleplay95.util.ToastUtil;
import com.tuwq.googleplay95.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class HotFragment extends BaseFragment {
    ScrollView scrollView;
    private FlowLayout flowLayout;

    int dp12,dp6,padding;

    @Override
    public View getSuccessView() {
        scrollView = new ScrollView(getContext());
        flowLayout = new FlowLayout(getContext());

        padding = DimenUtil.getDimens(R.dimen.dp15);
        dp12 = DimenUtil.getDimens(R.dimen.dp12);
        dp6 = DimenUtil.getDimens(R.dimen.dp6);

        //设置内边距
        flowLayout.setPadding(padding,padding,padding,padding);
        //设置水平和垂直间距
        flowLayout.setHorizontalSpacing(padding);
        flowLayout.setVerticalSpacing(padding);

//        flowLayout.setIsClearRemainSpace(false);

        scrollView.addView(flowLayout);

        return scrollView;
    }

    @Override
    public void loadData() {
        HttpHelper.create()
                .get(Url.Hot, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();
                        ArrayList<String> list = (ArrayList<String>) GsonUtil.parseJsonToList(result,new TypeToken<List<String>>(){}.getType());
                        //遍历list给flowLayout添加子View
                        for (int i = 0; i < list.size(); i++) {
                            final TextView textView = new TextView(getContext());
                            textView.setText(list.get(i));
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(16);
                            textView.setPadding(dp12,dp6,dp12,dp6);
                            GradientDrawable pressed = DrawableUtil.generateDrawable(padding);
                            GradientDrawable normal = DrawableUtil.generateDrawable(padding);
                            textView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed,normal));

                            flowLayout.addView(textView);

                            //设置点击事件
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showToast(textView.getText().toString());
                                }
                            });
                        }

                    }
                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }
}
