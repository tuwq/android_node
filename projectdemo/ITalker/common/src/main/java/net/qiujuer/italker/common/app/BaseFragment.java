package net.qiujuer.italker.common.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends androidx.fragment.app.Fragment {

    protected View mRoot;
    protected Unbinder mRootUnBinder;

    /**
     * 当fragment被添加到activity时调用
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layId = getContentLayoutId();
            // 初始化当前的根布局 但是不在创建时就添加到container里边
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 当View创建完成初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     * @param bundle 参数bundle
     * @return 如果参数正确返回true 错误返回false
     */
    protected void initArgs(Bundle bundle) {

    }

    /**
     * 得到当前界面的资源文件id
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * 绑定ButterKnife
     * @param root
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回按键触发时调用
     * @return 返回true代表我已处理返回逻辑,Activity不用自己Finish
     * 返回false代表我没有处理逻辑,Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
