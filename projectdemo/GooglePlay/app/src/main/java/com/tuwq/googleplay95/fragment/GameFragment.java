package com.tuwq.googleplay95.fragment;

import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.tuwq.googleplay95.adapter.HomeAdapter;
import com.tuwq.googleplay95.adapter.MyBaseAdapter;
import com.tuwq.googleplay95.bean.AppInfo;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends PtrListFragment<AppInfo> {
    @Override
    public MyBaseAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);
    }
    @Override
    public String getUrl() {
        return Url.Game + list.size();
    }
    @Override
    protected void parseDataAndUpdate(String result) {
        //将result解析为装有AppInfo的集合
        ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) GsonUtil.parseJsonToList(result, new TypeToken<List<AppInfo>>() {
        }.getType());
        if (appInfos != null) {
            list.addAll(appInfos);
            baseAdapter.notifyDataSetChanged();
        }
    }
}

