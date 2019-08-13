package com.tuwq.mobileplayer.fragment.vbangpage;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

import com.tuwq.mobileplayer.BaseFragment;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.activity.AudioPlayerActivity;
import com.tuwq.mobileplayer.adapter.VBangAdapter;
import com.tuwq.mobileplayer.bean.MusicBean;
import com.tuwq.mobileplayer.db.MyAsyncQueryHandler;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;

public class VBangFragment extends BaseFragment {
    private static final String TAG = "VBangFragment";

    @Bind(R.id.listview)
    ListView listview;
    private VBangAdapter vBangAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vbang;
    }

    @Override
    protected void initView() {
        // 显示列表
        vBangAdapter = new VBangAdapter(getContext(), null);
        listview.setAdapter(vBangAdapter);
        listview.setOnItemClickListener(new OnAudioItemClickListener());

        // 查询音乐数据
        ContentResolver resolver = getActivity().getContentResolver();
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] projection = {Media._ID/*"uid as _id"*/,Media.DATA, Media.SIZE, Media.DISPLAY_NAME, Media.ARTIST};

        // Cursor cursor = resolver.query(uri, projection, null, null, null);
        // Util.printCursor(cursor);
        // 异步查询数据库
        MyAsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(resolver);
        // token 用于区分不同类型的数据查询
        // cookie 要使用 cursor 数据的对象
        asyncQueryHandler.startQuery(0, vBangAdapter,uri, projection, null, null, null);
    }

    private class OnAudioItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) vBangAdapter.getItem(position);
            ArrayList<MusicBean> beanArrayList = MusicBean.listFromCursor(cursor);
            LogUtils.e(TAG,"OnAudioItemClickListener.onItemClick,beanArrayList="+beanArrayList);

            Intent intent = new Intent(getContext(), AudioPlayerActivity.class);
            intent.putExtra("data",beanArrayList);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }

}
