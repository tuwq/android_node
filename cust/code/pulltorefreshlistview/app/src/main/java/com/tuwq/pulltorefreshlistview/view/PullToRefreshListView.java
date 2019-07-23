package com.tuwq.pulltorefreshlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.tuwq.pulltorefreshlistview.R;

public class PullToRefreshListView extends ListView {

    public PullToRefreshListView(Context context) {
        this(context,null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);

        addHeader();
    }
    /**
     * 添加刷新头
     */
    private void addHeader() {
        View view = View.inflate(getContext(), R.layout.header, null);
        this.addHeaderView(view);//给listview添加头条目
    }

}
