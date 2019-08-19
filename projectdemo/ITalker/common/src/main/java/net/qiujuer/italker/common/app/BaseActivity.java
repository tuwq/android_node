package net.qiujuer.italker.common.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在界面未初始化之前调用的初始化窗口
        initWindows();
        if (initArgs(this.getIntent().getExtras())) {
            // 得到界面id并设置到界面中
            int layId = getContentLayoutId();
            this.setContentView(layId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {

    }

    /**
     * 初始化相关参数
     * @param bundle 参数bundle
     * @return 如果参数正确返回true 错误返回false
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面的资源文件id
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * 绑定ButterKnife
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时,Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 得到activity所有的fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if (fragments != null && fragments.size() > 0) {
            for(Fragment fragment: fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment){
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment)fragment).onBackPressed()) {
                        // 如果有直接return,不做逻辑处理
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
