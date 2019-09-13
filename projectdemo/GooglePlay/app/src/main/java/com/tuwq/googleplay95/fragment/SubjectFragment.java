package com.tuwq.googleplay95.fragment;

import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.tuwq.googleplay95.adapter.MyBaseAdapter;
import com.tuwq.googleplay95.adapter.SubjectAdapter;
import com.tuwq.googleplay95.bean.Subject;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends PtrListFragment<Subject>  {

    @Override
    public MyBaseAdapter getAdapter() {
        return new SubjectAdapter(list);
    }

    @Override
    public String getUrl() {
        return Url.Subject+list.size();
    }

    @Override
    protected void parseDataAndUpdate(String result) {
        //解析json
        ArrayList<Subject> subjects = (ArrayList<Subject>) GsonUtil.parseJsonToList(result,new TypeToken<List<Subject>>(){}.getType());
        if(subjects!=null){
            list.addAll(subjects);
            //更新adapter
            baseAdapter.notifyDataSetChanged();
        }
    }
}
