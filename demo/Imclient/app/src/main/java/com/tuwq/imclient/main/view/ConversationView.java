package com.tuwq.imclient.main.view;


import com.hyphenate.chat.EMConversation;

import java.util.List;

public interface ConversationView {
    void onGetConversations(List<EMConversation> conversationList);

    void onClearAllUnreadMark();
}
