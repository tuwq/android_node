package com.tuwq.vmplayer.fragment.vbangpage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tuwq.vmplayer.R;
import com.tuwq.vmplayer.activity.MusicPlayerActivity;
import com.tuwq.vmplayer.adapter.MyCursorAdapter;
import com.tuwq.vmplayer.fragment.BaseFragment;
import com.tuwq.vmplayer.util.Util;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/9/28.
 */

public class VBangFragment extends BaseFragment implements VbangContract.View {
    @Bind(R.id.lv_music)
    ListView lvMusic;
    private MyCursorAdapter myCursorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_vbang, container, false);
            ButterKnife.bind(this, rootView);
            initView();
            new VbangPresenter(this);
            presenter.query(getActivity(),myCursorAdapter);
//            initData();
        }

        return rootView;
    }

    private void initData() {
        //节目的数据怎么来呢，我们手机上很多多媒体的数据，比如说，图片，视频，音乐呢
        //都是以数据库的方式进行存储，系统内部又通过内容提供者暴露给第三方调用者，那介于这样一个道理呢
        //我们就要通过内容能提供者进行数据的访问。
        //ContentResolver.底层就是封装了对数据库的增删改查
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        //查询的路径
        //查询的列，需要的字段
        //查询条件，
        //查询条件的参数
        //排序
//        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
//        Util.printCursor(cursor);

        MyQueryHelp myQueryHelp = new MyQueryHelp(getActivity().getContentResolver(),this);
        myQueryHelp.startQuery(0,null,uri,projection,null,null,null);


    }

    private void initView() {
        showLoging();
        myCursorAdapter = new MyCursorAdapter(getActivity(), null);
        lvMusic.setAdapter(myCursorAdapter);
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) myCursorAdapter.getItem(position);
                Intent mIntent=new Intent(getActivity(), MusicPlayerActivity.class);
                mIntent.putExtra("position",position);
                mIntent.putExtra("musicList", (Serializable) Util.getMusicList(cursor));
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setData() {
        dismiss();
    }

    @Override
    public void setError(String error) {

    }
    private VbangContract.Prestener presenter;
    @Override
    public void setPresenter(VbangContract.Prestener presenter) {
        this.presenter=presenter;
    }
}
