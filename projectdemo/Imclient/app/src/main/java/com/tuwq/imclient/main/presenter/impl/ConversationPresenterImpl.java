package com.tuwq.imclient.main.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.tuwq.imclient.main.presenter.ConversationPresenter;
import com.tuwq.imclient.main.view.ConversationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ConversationPresenterImpl implements ConversationPresenter {
    private ConversationView conversationView;

    public ConversationPresenterImpl(ConversationView conversationView) {
        this.conversationView = conversationView;
    }

    @Override
    public void getConversations() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        Collection<EMConversation> values = allConversations.values();
        //获取会话的集合
        List<EMConversation> conversationList = new ArrayList<>(values);
        Collections.sort(conversationList, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {
                return (int) (o2.getLastMessage().getMsgTime()-o1.getLastMessage().getMsgTime());
            }
        });
        conversationView.onGetConversations(conversationList);
    }

    @Override
    public void clearAllUnreadMark() {
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
        conversationView.onClearAllUnreadMark();
    }
}
