package com.tuwq.imclient.chat.presenter;

import com.hyphenate.chat.EMMessage;

import java.util.List;

public interface ChatPresenter {
    /**
     * 获取聊天记录
     * @param username
     * @return
     */
    List<EMMessage> getChatHistoryMessage(String username);

    /**
     * 发送消息
     * @param msgText
     * @param contact
     */
    void sendMessage(String msgText, String contact);
}