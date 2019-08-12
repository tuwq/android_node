package com.tuwq.mobileplayer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.URLProviderUtil;
import com.tuwq.mobileplayer.bean.YueDanDetailBean;
import com.tuwq.mobileplayer.fragment.playerpage.CommentFragment;
import com.tuwq.mobileplayer.fragment.playerpage.DescriptionFragment;
import com.tuwq.mobileplayer.fragment.playerpage.RelativeFragment;
import com.tuwq.mobileplayer.http.BaseCallBack;
import com.tuwq.mobileplayer.http.HttpManager;
import com.tuwq.mobileplayer.utils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {
    private static final String TAG = "PlayerActivity";

    @Bind(R.id.jiecaoplayer)
    JCVideoPlayerStandard jiecaoplayer;
    @Bind(R.id.mv_describe)
    ImageView mvDescribe;
    @Bind(R.id.mv_comment)
    ImageView mvComment;
    @Bind(R.id.mv_relative)
    ImageView mvRelative;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    private SparseArray<Fragment> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        String type = getIntent().getStringExtra("type");
        if ("yuedan".equals(type)) {
            // 处理悦单
            int id = getIntent().getIntExtra("id", -1);
            requestVideoData(id);
        } else {
            // 处理 Mv 播放
            String url = getIntent().getStringExtra("url");
            String title = getIntent().getStringExtra("title");
            // 初始化播放器
            jiecaoplayer.setUp(url, title);
            // 模拟点击按钮
            jiecaoplayer.startButton.performClick();
        }
        // 初始化 Fragment 缓存
        sparseArray = new SparseArray<>();
        sparseArray.append(R.id.mv_describe,new DescriptionFragment());
        sparseArray.append(R.id.mv_comment,new CommentFragment());
        sparseArray.append(R.id.mv_relative,new RelativeFragment());
        // 默认选中 MV 描述界面
        mvDescribe.performClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    /**
     * 请求视频数据
     * @param id
     */
    private void requestVideoData(int id) {
        String url = URLProviderUtil.getPeopleYueDanList(id);
        LogUtils.e(TAG,"PlayerActivity.requestVideoData,url="+url);
        HttpManager.getInstance().get(url, new BaseCallBack<YueDanDetailBean>() {
            @Override
            public void onFailure(int code, Exception e) {

            }
            @Override
            public void onSuccess(YueDanDetailBean yueDanDetailBean) {
                List<YueDanDetailBean.VideosBean> videos = yueDanDetailBean.getVideos();
                YueDanDetailBean.VideosBean videosBean = videos.get(0);
                // 初始化播放器
                jiecaoplayer.setUp(videosBean.getUrl(), videosBean.getTitle());
                // 模拟点击按钮
                jiecaoplayer.startButton.performClick();
            }
        });
    }

    /**
     * 点击图片事件进行切换
     * @param view
     */
    @OnClick({R.id.mv_describe, R.id.mv_comment, R.id.mv_relative})
    public void onClick(View view) {
        updateButtonPic(view.getId());
        showFragment(sparseArray.get(view.getId()));
    }

    /**
     * 将参数里的 Fragment 显示到界面上
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content,fragment);
        transaction.commit();
    }

    /**
     * 根据选中的图片切换
     * @param viewId
     */
    private void updateButtonPic(int viewId) {
        // 更新简介
        if (viewId == R.id.mv_describe){
            mvDescribe.setBackgroundResource(R.drawable.player_mv_p);
        }else{
            mvDescribe.setBackgroundResource(R.drawable.player_mv);
        }

        // 更新评论
        if (viewId == R.id.mv_comment){
            mvComment.setBackgroundResource(R.drawable.player_comment_p);
        }else {
            mvComment.setBackgroundResource(R.drawable.player_comment);
        }

        // 更新评论
        if (viewId == R.id.mv_relative){
            mvRelative.setBackgroundResource(R.drawable.player_relative_mv_p);
        }else {
            mvRelative.setBackgroundResource(R.drawable.player_relative_mv);
        }
    }
}
