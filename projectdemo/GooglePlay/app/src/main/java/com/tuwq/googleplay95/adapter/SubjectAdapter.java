package com.tuwq.googleplay95.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tuwq.googleplay95.R;
import com.tuwq.googleplay95.bean.Subject;
import com.tuwq.googleplay95.global.UILOption;
import com.tuwq.googleplay95.http.Url;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubjectAdapter extends MyBaseAdapter<Subject> {
    public SubjectAdapter(ArrayList<Subject> list) {
        super(list);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_subject;
    }

    @Override
    protected Object createViewHolder(View convertView, int position) {
        return new SubjectHolder(convertView);
    }

    @Override
    protected void bindViewHolder(Subject subject, Object holder, int position) {
        SubjectHolder subjectHolder = (SubjectHolder) holder;
        subjectHolder.tvTitle.setText(subject.des);
        ImageLoader.getInstance().displayImage(Url.IMG_PREFIX+subject.url,
                subjectHolder.ivImage, UILOption.options);
    }

    static class SubjectHolder {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        SubjectHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
