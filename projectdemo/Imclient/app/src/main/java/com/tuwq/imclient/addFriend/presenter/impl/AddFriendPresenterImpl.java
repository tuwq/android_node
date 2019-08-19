package com.tuwq.imclient.addFriend.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tuwq.imclient.addFriend.presenter.AddFriendPresenter;
import com.tuwq.imclient.addFriend.view.AddFriendView;
import com.tuwq.imclient.db.DBUtils;
import com.tuwq.imclient.splash.model.User;
import com.tuwq.imclient.utils.ThreadUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AddFriendPresenterImpl implements AddFriendPresenter {
    private AddFriendView addFriendView;

    public AddFriendPresenterImpl(AddFriendView addFriendView) {
        this.addFriendView = addFriendView;
    }

    @Override
    public void searchFriend(String keyword) {
        final String currentUser = EMClient.getInstance().getCurrentUser();
        //BmobQuery 要到bmob服务端查询数据
        BmobQuery<User> query = new BmobQuery<>();
        //模糊查询用户名  以输入的字母开头的都查出来
        query.addWhereStartsWith("username",keyword)
                //要把自己去掉 查询其它用户
                .addWhereNotEqualTo("username",currentUser)
                //查询满足条件的所有记录
                .findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(e==null&&list!=null&&list.size()>0){
                            //查出数据可以显示
                            //从数据库中拿到已经在通讯录中的好友
                            List<String> users = DBUtils.initContact(currentUser);
                            addFriendView.onQuerySuccess(list,users,true,null);
                        }else{
                            if(e == null){
                                //查询成功但是没有匹配的数据
                                addFriendView.onQuerySuccess(null,null,false,"没有满足条件的用户");
                            }else{
                                //查询失败
                                addFriendView.onQuerySuccess(null,null,false,e.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    public void addFriend(final String username) {
        ThreadUtils.runOnNonUIThread(new Runnable() {
            @Override
            public void run() {
                try {

                    EMClient.getInstance().contactManager().addContact(username,"申请添加好友");
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            addFriendView.onGetAddFriendResult(true,null);
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            addFriendView.onGetAddFriendResult(true,e.getMessage());
                        }
                    });
                }
            }
        });
    }
}

