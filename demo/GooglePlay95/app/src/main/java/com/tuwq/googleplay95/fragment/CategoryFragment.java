package com.tuwq.googleplay95.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.adapter.CategoryAdapter;
import com.tuwq.googleplay95.bean.Category;
import com.tuwq.googleplay95.http.HttpHelper;
import com.tuwq.googleplay95.http.Url;
import com.tuwq.googleplay95.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment {
    private ListView listView;
    //存放title和SubCategory的集合
    ArrayList<Object> list = new ArrayList<>();

    @Override
    public View getSuccessView() {
        listView = (ListView) View.inflate(getContext(), R.layout.listview,null);

        return listView;
    }

    @Override
    public void loadData() {
        HttpHelper.create()
                .get(Url.Category, new HttpHelper.HttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        stateLayout.showSuccessView();

                        ArrayList<Category> categories = (ArrayList<Category>) GsonUtil.parseJsonToList(result,new TypeToken<List<Category>>(){}.getType());
                        //集合中放的是大分类数据
                        //将所有Category的title和SubCategory放入list中
                        for(Category cate : categories){
                            //1.将title放入list中
                            list.add(cate.title);
                            //2.将infos的所有SubCategory放入list
                            list.addAll(cate.infos);
                        }

                        //设置adapter
                        listView.setAdapter(new CategoryAdapter(list));

                    }
                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }
}
