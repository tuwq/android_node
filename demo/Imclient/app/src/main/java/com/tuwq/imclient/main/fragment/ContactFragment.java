package com.tuwq.imclient.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuwq.imclient.R;
import com.tuwq.imclient.main.adapter.ContactAdapter;
import com.tuwq.imclient.main.layout.ContactLayout;
import com.tuwq.imclient.main.presenter.ContactPresenter;
import com.tuwq.imclient.main.presenter.impl.ContactPresenterImpl;
import com.tuwq.imclient.main.view.ContactView;
import com.tuwq.imclient.utils.ToastUtils;

import java.util.List;

public class ContactFragment extends BaseFragment implements ContactView {

    private ContactLayout contactLayout;
    private ContactPresenter presenter;
    private ContactAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        contactLayout = (ContactLayout) view.findViewById(R.id.contactlayout);
        presenter = new ContactPresenterImpl(this);
        presenter.initContact();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onInitContact(List<String> contact) {
        //获取了初始化数据 需要通过recyclerview进行展示
        //setAdapter
        adapter = new ContactAdapter(contact);
        contactLayout.setAdapter(adapter);
    }

    @Override
    public void onUpdateContact(List<String> contact, boolean isUpdateSuccess, String errorMsg) {
        if(isUpdateSuccess){
            adapter.setContacts(contact);
            adapter.notifyDataSetChanged();
        }else{
            ToastUtils.showToast(getActivity(),"新联系人失败!");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            //隐藏了
        }else{
            //显示了
        }
        super.onHiddenChanged(hidden);
    }
}

