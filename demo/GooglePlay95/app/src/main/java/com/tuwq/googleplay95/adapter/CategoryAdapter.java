package com.tuwq.googleplay95.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.SubCategory;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryAdapter extends MyBaseAdapter<Object> {
    public CategoryAdapter(ArrayList<Object> list) {
        super(list);
    }

    //1.先定义条目类型的变量
    public final int ITEM_TITLE = 0;//标题类型
    public final int ITEM_SUB = 1;//子分类类型

    /**
     * 返回条目类型的总数
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取指定position的条目是什么类型的
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //根据集合中的数据类型来判断
        Object obj = list.get(position);
        if (obj instanceof String) {
            //说明是标题类型
            return ITEM_TITLE;
        } else {
            //说明是子分类的类型
            return ITEM_SUB;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getItemLayoutId(int position) {
        //根据条目类型返回布局
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                //返回标题的布局
                return R.layout.adapter_category_title;
            case ITEM_SUB:
                //返回子分类的布局
                return R.layout.adapter_category_sub;
        }
        return 0;
    }

    @Override
    protected Object createViewHolder(View convertView, int position) {
        //根据条目类型返回对应的holder对象
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                //返回标题的holder
                return new TitleHolder(convertView);
            case ITEM_SUB:
                //返回子分类的holder
                return new SubHolder(convertView);
        }
        return null;
    }

    @Override
    protected void bindViewHolder(Object obj, Object holder, int position) {
        //根据条目类型来绑定数据
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                //绑定标题的数据
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.tvTitle.setText((String)obj);
                break;
            case ITEM_SUB:
                //返回子分类的holder
                SubHolder subHolder = (SubHolder) holder;
                SubCategory subCategory = (SubCategory)obj;

                //显示第一个
                subHolder.tvName1.setText(subCategory.name1);
                ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+subCategory.url1,subHolder.ivImage1
                        , UILOption.options);

                //由于第2个和第3个可能木有，所以需要判断
                ViewGroup parent2 = (ViewGroup) subHolder.ivImage2.getParent();
                if(!TextUtils.isEmpty(subCategory.name2) && !TextUtils.isEmpty(subCategory.url2)){
                    //当需要显示的时候，重新设置为可见
                    parent2.setVisibility(View.VISIBLE);

                    subHolder.tvName2.setText(subCategory.name2);
                    ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+subCategory.url2,subHolder.ivImage2
                            , UILOption.options);
                }else {
                    //说明需要隐藏第2个
                    parent2.setVisibility(View.GONE);
                }


                ViewGroup parent3 = (ViewGroup) subHolder.ivImage3.getParent();
                if(!TextUtils.isEmpty(subCategory.name3) && !TextUtils.isEmpty(subCategory.url3)){
                    //当需要显示的时候，重新设置为可见
                    parent3.setVisibility(View.VISIBLE);

                    subHolder.tvName3.setText(subCategory.name3);
                    ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+subCategory.url3,subHolder.ivImage3
                            , UILOption.options);
                }else {
                    //说明需要隐藏第3个
                    parent3.setVisibility(View.GONE);
                }
                break;
        }
    }

    static class TitleHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;

        TitleHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class SubHolder {
        @Bind(R.id.iv_image1)
        ImageView ivImage1;
        @Bind(R.id.tv_name1)
        TextView tvName1;
        @Bind(R.id.iv_image2)
        ImageView ivImage2;
        @Bind(R.id.tv_name2)
        TextView tvName2;
        @Bind(R.id.iv_image3)
        ImageView ivImage3;
        @Bind(R.id.tv_name3)
        TextView tvName3;

        SubHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}