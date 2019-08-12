package com.tuwq.mobileplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.tuwq.mobileplayer.R;
import com.tuwq.mobileplayer.fragment.TestFragment;
import com.tuwq.mobileplayer.fragment.homepage.HomeFragment;
import com.tuwq.mobileplayer.fragment.mvpage.MvFragment;
import com.tuwq.mobileplayer.fragment.yuedanpage.YueDanFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.toobar)
    Toolbar toolbar;
    // 存放fragment映射的hashMap
    private SparseArray<Fragment> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 将Toobar 设置为标题栏
        this.setSupportActionBar(toolbar);
        // 修改Toobar 属性
        this.getSupportActionBar().setTitle("VMPlayer");

        // 初始化 Fragment 集合
        sparseArray = new SparseArray<>();
        sparseArray.append(R.id.bottombar_home,new HomeFragment());
        sparseArray.append(R.id.bottombar_mv, new MvFragment());
        sparseArray.append(R.id.bottombar_vbang,TestFragment.newInstance("V榜"));
        sparseArray.append(R.id.bottombar_yuedan, new YueDanFragment());

        // 处理底部栏
        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottombar, new OnMenuTabClickListener());
    }

    /**
     * 创建TooBar的选择菜单
     * @param menu 菜单
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 创建menu菜单,这个菜单会依附到TooBar上
        this.getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 选择菜单被点击
     * @param item 被点击的菜单
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理menu菜单的点击监听
        switch (item.getItemId()) {
            case R.id.menu_main_settings:
                // 跳转到设置界面
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class  OnMenuTabClickListener implements com.roughike.bottombar.OnMenuTabClickListener {
        /**
         * 选中了某一个Fragment
         * @param menuItemId
         */
        @Override
        public void onMenuTabSelected(int menuItemId) {
            Toast.makeText(MainActivity.this, "选中了一个 条目", Toast.LENGTH_SHORT).show();
            // 切换到当前按钮对应的 Fragment
            Fragment fragment = sparseArray.get(menuItemId);
            switchFragment(fragment);
        }
        /**
         * 重复选中了某一个Fragment
         * @param menuItemId
         */
        @Override
        public void onMenuTabReSelected(int menuItemId) { }
    }

    /**
     * 切换bottomBar选中的fragment
     * @param fragment
     */
    private void switchFragment(Fragment fragment)   {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


}
