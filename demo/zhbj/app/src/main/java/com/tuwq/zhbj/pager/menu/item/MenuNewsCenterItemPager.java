package com.tuwq.zhbj.pager.menu.item;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tuwq.zhbj.R;
import com.tuwq.zhbj.base.BaseMenupager;
import com.tuwq.zhbj.bean.NewBean;
import com.tuwq.zhbj.net.NetUrl;
import com.tuwq.zhbj.tool.SharedPreferencesTool;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * 嵌套在MenuNewsCenterpager页面中的viewpager中的页面
 */
public class MenuNewsCenterItemPager extends BaseMenupager {

    private String mUrl;
    private View mListViewView;
    private View mViewPagerView;

    //通过反射的形式，实现findviewbyid的操作
    @ViewInject(R.id.menunewscenteritem_lv_listview)
    private ListView mListView;

    @ViewInject(R.id.menunewscenteritem_vp_viewpager)
    public ViewPager mViewPager;

    @ViewInject(R.id.menunewscenteritem_tv_text)
    public TextView mText;

    @ViewInject(R.id.menunewscenter_cpi_indicator)
    public CirclePageIndicator mIndicator;

    /**ViewPager图片地址的路径集合**/
    private List<String> imageUrls = new ArrayList<String>();
    /**ViewPager标题文本的集合**/
    private List<String> titiles = new ArrayList<String>();

    private List<NewBean.News> news = new ArrayList<NewBean.News>();

    private Myadapter myadapter;
    private MyListViewAdapter myListViewAdapter;

    //界面所需的数据并没有在新闻中心中一起获取，需要我们拿到路径之后，采取请求服务器获取数据
    public MenuNewsCenterItemPager(Activity activity,String url) {
        super(activity);
        this.mUrl = url;
    }

    @Override
    public View initView() {

        //因为当前的界面有viewpager和listview两部份布局组成，所以加载的也要加载两部分的控件
        mViewPagerView = View.inflate(activity, R.layout.menunewscenteritem_viewpager, null);
        mListViewView = View.inflate(activity, R.layout.menunewscenteritem_listview, null);

        //将添加了注解的控件全部初始化出来
        //handler : 要在那个界面中初始化控件
        //view : 要在布局文件初始化控件
        ViewUtils.inject(this, mListViewView);
        ViewUtils.inject(this, mViewPagerView);

        //因为viewpager最终是要添加到listview中显示的，所以界面的最终布局是listview
        return mListViewView;
    }

    @Override
    public void initData() {
        //请求服务器，获取最终界面的数据
        //2.再次请求服务器，判断是否有缓存数据
        String sp_msg = SharedPreferencesTool.getString(activity, NetUrl.SERVERURL+mUrl, "");
        if (!TextUtils.isEmpty(sp_msg)) {
            processJson(sp_msg);
        }
        getData();
    }
    /**
     * 请求服务器获取数据
     */
    private void getData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, NetUrl.SERVERURL+mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;

                //1.请求数据成功，缓存数据
                SharedPreferencesTool.saveString(activity, NetUrl.SERVERURL+mUrl, json);
                //3.解析最新数据
                processJson(json);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }


    /**
     * 解析json数据操作
     *@param json
     */
    private void processJson(String json) {
        Gson gson = new Gson();
        NewBean newBean = gson.fromJson(json, NewBean.class);

        showMsg(newBean);
    }


    /**
     * 获取数据展示数据
     */
    private void showMsg(NewBean newBean) {
        //展示viewpager的数据
        //判断是否有图片
        if (newBean.data.topnews.size() > 0) {
            imageUrls.clear();
            titiles.clear();
            //获取viewpager所需的数据
            for (int i = 0; i < newBean.data.topnews.size(); i++) {
                imageUrls.add(newBean.data.topnews.get(i).topimage);
                titiles.add(newBean.data.topnews.get(i).title);
            }
            //通过viewpager展示图片
            if (myadapter == null) {
                myadapter = new Myadapter();
                mViewPager.setAdapter(myadapter);
            }else{
                myadapter.notifyDataSetChanged();
            }
            //因为点事使用第三方框架实现的，所以需要和viwpager关联起来
            mIndicator.setViewPager(mViewPager);
            mIndicator.setSnap(true);//以快照的方式显示点

            //初始化操作
            //设置当第一次进入的界面，设置默认显示第一个文本和第一个点
            mText.setText(titiles.get(0));
            mIndicator.onPageSelected(0);//选中哪个点

            //将viewpager设置给listview展示viewpager
            //getHeaderViewsCount : 获取listview添加的头条目的个数
            if (mListView.getHeaderViewsCount() < 1) {
                mListView.addHeaderView(mViewPagerView);//将viewPager的布局添加到listview中
            }
        }
        //获取listview的数据，展示listview的数据
        if (newBean.data.news.size() > 0) {
            news = newBean.data.news;
            //设置listview的adapter显示数据

            if (myListViewAdapter == null) {
                myListViewAdapter = new MyListViewAdapter();
                mListView.setAdapter(myListViewAdapter);
            }else{
                myListViewAdapter.notifyDataSetChanged();
            }
        }
    }

    /**ViewPager的adapter**/
    private class Myadapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(activity, R.layout.menunewscenteritem_viewpager_item, null);
            ImageView mIcon = (ImageView) view.findViewById(R.id.menunewscenteritem_viewpageritem_iv_icon);

            String imageurl = imageUrls.get(position);
            BitmapUtils bitmapUtils = new BitmapUtils(activity);
            //根据图片的路径获取图片，并存放到相应的控件中
            //参数1：存放图片的控件
            //参数2：图片的url路径
            bitmapUtils.display(mIcon, imageurl);

            //将imageView所在的布局文件设置给viewpager显示
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    /**ListView的adapter**/
    private class MyListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return news.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(activity);
            textView.setText("11111111");
            return textView;
        }
    }
}
