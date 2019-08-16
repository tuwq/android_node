package com.tuwq.imclient.chat.presenter.impl;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.tuwq.imclient.chat.presenter.ChatPresenter;

import java.util.List;

public class ChatPresenterImpl implements ChatPresenter {
    @Override
    public List<EMMessage> getChatHistoryMessage(String username) {
        EMChatManager emChatManager = EMClient.getInstance().chatManager();
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        //返回服务器的所有条消息
        conversation.getAllMessages();
        //从数据库库获取数据 第一个参数 消息的id 从这条消息开始 取第二个参数传入的数目 这么多条数据
        EMMessage lastMessage = conversation.getLastMessage();
        String msgId = lastMessage.getMsgId();
        List<EMMessage> emMessages = conversation.loadMoreMsgFromDB(msgId, 20);
        return emMessages;
    }
}
