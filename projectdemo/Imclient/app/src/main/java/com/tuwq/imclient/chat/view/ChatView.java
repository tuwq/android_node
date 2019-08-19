package com.tuwq.imclient.chat.view;

import com.hyphenate.chat.EMMessage;

import java.util.List;

public interface ChatView {
    void getHistoryMessage(List<EMMessage> emMessages);

    void updateList();
}

