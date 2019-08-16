package com.tuwq.imclient.chat.presenter;

import com.hyphenate.chat.EMMessage;

import java.util.List;

public interface ChatPresenter {
    List<EMMessage> getChatHistoryMessage(String username);
}
