package com.tuwq.imclient.addFriend.view;


import com.tuwq.imclient.splash.model.User;

import java.util.List;

public interface AddFriendView {

    void onQuerySuccess(List<User> list, List<String> users, boolean b, String errorMeg);

    void onGetAddFriendResult(boolean b, String message);
}
