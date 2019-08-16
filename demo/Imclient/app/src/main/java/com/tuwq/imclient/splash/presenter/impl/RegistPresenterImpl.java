package com.tuwq.imclient.splash.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tuwq.imclient.splash.model.User;
import com.tuwq.imclient.splash.presenter.RegistPresenter;
import com.tuwq.imclient.splash.view.RegistView;
import com.tuwq.imclient.utils.ThreadUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegistPresenterImpl implements RegistPresenter {
    private RegistView registView;

    public RegistPresenterImpl(RegistView registView) {
        this.registView = registView;
    }

    @Override
    public void registUser(final String username, final String pwd) {
        //注册的逻辑
        //先去bmob注册 注册成功之后 再到环信注册 注册bmob的过程就是创建一个user对象 放到三方的数据库中
        final User user = new User();
        //设置用户名密码
        user.setUsername(username);
        user.setPassword(pwd);
        //保存user对象到bmob服务器
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                {
                    //如果 没有异常说明注册成功
                    if(e==null){
                        ThreadUtils.runOnNonUIThread(new Runnable() {
                            @Override
                            public void run() {
                                //注册环信
                                try {
                                    EMClient.getInstance().createAccount(username, pwd);
                                    //说明注册成功
                                    //通知界面跳转
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            registView.onGetRegistState(username,pwd,true,null);
                                        }
                                    });

                                } catch (final HyphenateException e1) {
                                    e1.printStackTrace();
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //如果注册失败 删除user
                                            user.delete();
                                            //通知界面显示注册失败
                                            registView.onGetRegistState(username,pwd,false,e1.getDescription());
                                        }
                                    });
                                }
                            }
                        });

                    }else{
                        //如果有异常说明注册失败 通知界面显示注册失败
                        registView.onGetRegistState(username,pwd,false,e.getMessage());
                    }
                }
            }
        });
//        user.save(new SaveListener() {
//            @Override
//            public void done(Object o, BmobException e) {
//                //如果 没有异常说明注册成功
//                if(e==null){
//                    ThreadUtils.runOnNonUIThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //注册环信
//                            try {
//                                EMClient.getInstance().createAccount(username, pwd);
//                                //说明注册成功
//                                //通知界面跳转
//                                registView.onGetRegistState(username,pwd,true,null);
//                            } catch (final HyphenateException e1) {
//                                e1.printStackTrace();
//                                ThreadUtils.runOnMainThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //如果注册失败 删除user
//                                        user.delete();
//                                        //通知界面显示注册失败
//                                        registView.onGetRegistState(username,pwd,false,e1.getMessage());
//                                    }
//                                });
//                            }
//                        }
//                    });
//
//                }else{
//                //如果有异常说明注册失败 通知界面显示注册失败
//                    registView.onGetRegistState(username,pwd,false,e.getMessage());
//                }
//            }
//        });
        //环信注册成功 用户注册成功
        //环信注册失败 删除bmob账户 注册失败
        //如果bmob注册失败 注册失败
    }
}

