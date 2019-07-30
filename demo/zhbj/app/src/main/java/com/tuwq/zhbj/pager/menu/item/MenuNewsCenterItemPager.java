package com.tuwq.zhbj.pager.menu.item;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove;
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
import com.tuwq.zhbj.tool.Constants;
import com.tuwq.zhbj.tool.SharedPreferencesTool;
import com.tuwq.zhbj.ui.PullToRefreshListView;
import com.tuwq.zhbj.ui.RollViewPager;
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
    private PullToRefreshListView mListView;

    @ViewInject(R.id.menunewscenteritem_vp_viewpager)
    public RollViewPager mViewPager;

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
    private Handler handler;
    private String loadmoreUrl;
    private List<String> readids = new ArrayList<String>();

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

        //将viewpager的布局添加到listview的刷新头布局操作
        mListView.setViewPager(mViewPagerView);

        //因为viewpager最终是要添加到listview中显示的，所以界面的最终布局是listview
        return mListViewView;
    }

    @Override
    public void initData() {
        //实现下拉刷新数据和上拉加载数据
        mListView.setOnRefreshListener(new PullToRefreshListView.OnReFreshListener() {
            @Override
            public void refresh() {
                getData(mUrl,false);
            }
            @Override
            public void loadMore() {
                //在原有的数据+新的数据
                getData(loadmoreUrl,true);
            }
        });

        //设置listview的条目点击事件，修改条目的已读未读样式
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //判断新闻是否已读，如果没有已读，变成已读的样式
                if (news.get(position-1).isRead == false) {
                    news.get(position-1).isRead = true;
                    myListViewAdapter.notifyDataSetChanged();
                    //将已读的新闻标示保存起来
                    //#id#id#id#id
                    String sp_readid = SharedPreferencesTool.getString(activity, Constants.NEWSREAD, "");
                    SharedPreferencesTool.saveString(activity, Constants.NEWSREAD, sp_readid+"#"+news.get(position-1).id);
                }
            }
        });

        //请求服务器，获取最终界面的数据
        //2.再次请求服务器，判断是否有缓存数据
        String sp_msg = SharedPreferencesTool.getString(activity, NetUrl.SERVERURL+mUrl, "");
        if (!TextUtils.isEmpty(sp_msg)) {
            processJson(sp_msg,false);
        }
        getData(mUrl,false);
    }
    /**
     * 请求服务器获取数据
     */
    private void getData(final String url,final boolean isLoadMore) {
        //当打开界面的时候，获取保存的已读新闻的id
        String readid = SharedPreferencesTool.getString(activity, Constants.NEWSREAD, "");
        if (!TextUtils.isEmpty(readid)) {
            //#id#id
            String[] split = readid.split("#");
            //将已读的新闻的id保存到集合中方便后续操作
            readids.clear();
            for (int i = 0; i < split.length; i++) {
                readids.add(split[i]);
            }
        }

        //判断请求的路径是否为空
        if (!TextUtils.isEmpty(url)) {
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpMethod.GET, NetUrl.SERVERURL+url, new RequestCallBack<String>() {

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String json = responseInfo.result;

                    //1.请求数据成功，缓存数据
                    SharedPreferencesTool.saveString(activity, NetUrl.SERVERURL+url, json);
                    //3.解析最新数据
                    processJson(json,isLoadMore);
                }
                @Override
                public void onFailure(HttpException error, String msg) {

                }
            });
        }else{
            Toast.makeText(activity, "没有最新数据了", Toast.LENGTH_SHORT).show();
            //取消刷新
            mListView.finish();
        }
    }


    /**
     * 解析json数据操作
     *@param json
     */
    private void processJson(String json,boolean isLoadMore) {
        Gson gson = new Gson();
        NewBean newBean = gson.fromJson(json, NewBean.class);

        showMsg(newBean,isLoadMore);
    }


    /**
     * 获取数据展示数据
     */
    private void showMsg(NewBean newBean, boolean isLoadMore) {
        //获取加载更多路径
        loadmoreUrl = newBean.data.more;

        //因为下拉刷新和加载更多都会执行showMsg来展示数据，但是加载更多是不需要更改原来的viewpager中的数据
        if (!isLoadMore) {
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


                //监听viewpager的界面切换监听，实现切换界面，更换文本
                mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        mText.setText(titiles.get(position));
                        //mIndicator.onPageSelected(position);
                    }
                    @Override
                    public void onPageScrolled(int position, float positionOffset,
                                               int positionOffsetPixels) {

                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                //初始化操作
                //设置当第一次进入的界面，设置默认显示第一个文本和第一个点
                mText.setText(titiles.get(0));
                mIndicator.onPageSelected(0);//选中哪个点
                mViewPager.setCurrentItem(0);//设置初始化第一个显示图片

                //将viewpager设置给listview展示viewpager
                //getHeaderViewsCount : 获取listview添加的头条目的个数
				/*if (mListView.getHeaderViewsCount() < 1) {
					mListView.addHeaderView(mViewPagerView);//将viewPager的布局添加到listview中
				}*/

                //通过handler实现viewpager的自动滚动操作
                //因为showMsg方法实在ProcessJson方中调用的，但是ProcessJson会在缓存和请求到最新数据的调用，所以会造成发送两次延迟消息
                if (handler == null) {
                    handler = new Handler(){
                        public void handleMessage(android.os.Message msg) {
                            //切换viewpager的界面
                            //获取当前显示界面的索引
                            int currentItem = mViewPager.getCurrentItem();
                            //计算下一个界面的索引
                            //判断如果是最后一个，切换到第一个
                            if (currentItem == imageUrls.size()-1) {
                                currentItem=0;
                            }else{
                                currentItem++;
                            }
                            //显示下一个界面
                            mViewPager.setCurrentItem(currentItem);
                            //切换完成一次，还要接着切换下一次
                            handler.sendEmptyMessageDelayed(0, 3000);//方法执行一次，发送一次延迟消息，不执行就不发送
                        };
                    };
                    handler.sendEmptyMessageDelayed(0, 3000);
                }

            }
        }

        //获取listview的数据，展示listview的数据
        if (newBean.data.news.size() > 0) {
            //下拉刷新，使用获取的数据即可，但是如果是加载更多的，是原数据+新的数据
            if (isLoadMore) {
                news.addAll(newBean.data.news);
            }else{
                news = newBean.data.news;
            }

            //当获取到数据的时候，还要根据已读新闻的id去更改list集合中数据，这样在显示数据的时候再getview方法中就可以根据标示更改样式
            for (NewBean.News info : news) {
                if (readids.contains(info.id)) {
                    info.isRead = true;
                }else{
                    info.isRead = false;
                }
            }

            //设置listview的adapter显示数据
            if (myListViewAdapter == null) {
                myListViewAdapter = new MyListViewAdapter();
                mListView.setAdapter(myListViewAdapter);
            }else{
                myListViewAdapter.notifyDataSetChanged();
            }
        }
        //加载数据成功，取消刷新操作
        mListView.finish();
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

            // 设置图片的触摸事件,当按下图片的时候,停止滚动操作,当抬起的时候,重新进行滚动操作
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // 不发送延迟消息,不进行自动滚动
                            handler.removeCallbacksAndMessages(null);//如果是null，表示清除所有的消息
                            break;
                        case MotionEvent.ACTION_UP:
                            // 重新进行自动滚动操作
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                    }
                    return true;
                }
            });
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
            if (convertView == null) {
                convertView =  View.inflate(activity, R.layout.menunewscenteritem_listview_item, null);;
            }
            ImageView mIcon = (ImageView) convertView.findViewById(R.id.item_iv_icon);
            TextView mTitle = (TextView) convertView.findViewById(R.id.item_tv_title);
            TextView mTime = (TextView) convertView.findViewById(R.id.item_tv_time);
            NewBean.News info = news.get(position);
            mTitle.setText(info.title);
            mTime.setText(info.pubdate);

            BitmapUtils bitmapUtils = new BitmapUtils(activity);
            bitmapUtils.display(mIcon, info.listimage);

            //根据新闻已读未读的标示设置显示样式
            if (news.get(position).isRead) {
                mTitle.setTextColor(Color.GRAY);
            }else{
                mTitle.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
}
