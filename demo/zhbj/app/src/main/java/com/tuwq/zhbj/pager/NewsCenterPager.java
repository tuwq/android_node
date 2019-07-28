package com.tuwq.zhbj.pager;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tuwq.zhbj.HomeActivity;
import com.tuwq.zhbj.base.BasePager;
import com.tuwq.zhbj.tool.SharedPreferencesTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心的界面
 * 1.因为，新闻中心，智慧服务等界面也要使用普通的java类进行操作，所以每个java中都会有加载界面显示数据的操作，
 * 相同操作，抽取到父类
 */
public class NewsCenterPager extends BasePager {

    private List<String> titles;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    //但是普通的java类不能返回界面的view对象，所以在java类中创建一个initView方法，在initView方法中可以通过View.inflate形式将布局文件转化成view对象，返回

    @Override
    public void initData() {
        TextView textView = new TextView(activity);
        textView.setText("新闻中心");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);

        //将子类的显示内容，填充到父类显示区域显示
        mContent.addView(textView);

        //设置子类的标题
        mTitle.setText("新闻");

        //隐藏menu菜单
        mMenu.setVisibility(View.VISIBLE);

        //请求网络，获取服务器回传的数据
        //2.再次请求服务的时候，查看是否缓存数据，有缓存数据，加载显示缓存数据，没有缓存数据，直接请求网络获取数据
       /* String sp_msg = SharedPreferencesTool.getString(activity, Constants.NEWSCENTERMSG, "");
        if (!TextUtils.isEmpty(sp_msg)) {
            //加载显示缓存数据
            processJSON(sp_msg);
        }
        getData();*/

        super.initData();
    }

    /**
     * 请求服务器获取数据
     *//*
    private void getData() {
        //1.联网权限      2.使用xutils
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, NetUrl.NEWSCENTERURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                //1.请求服务器成功，将服务器返回的数据，保存到本地
                SharedPreferencesTool.saveString(activity, Constants.NEWSCENTERMSG, result);
                //3.加载解析获取最新数据，将原来的缓存数据覆盖
                processJSON(result);
            }
            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    *//**
     * 解析json数据
     *@param sp_msg
     *//*
    private void processJSON(String json) {
        Gson gson = new Gson();
        NewsCenterInfo newsCenterInfo = gson.fromJson(json, NewsCenterInfo.class);

        System.out.println(newsCenterInfo.data.get(0).title);

        //展示数据
        //获取menufragment所需的数据，传递给menufragment进行显示操作
        setMenuFragmentMsg(newsCenterInfo);
    }

    *//**
     * 获取menufragment所需的数据，传递给menufragment进行显示
     *@param newsCenterInfo
     *//*
    private void setMenuFragmentMsg(NewsCenterInfo newsCenterInfo) {
        //获取menuFragment所需的数据
        titles = new ArrayList<String>();
        titles.clear();
        for (int i = 0; i < newsCenterInfo.data.size(); i++) {
            titles.add(newsCenterInfo.data.get(i).title);
        }
        //将数据传递给MenuFragment
        ((HomeActivity)activity).getMenuFragment().initList(titles);
    }*/
}
