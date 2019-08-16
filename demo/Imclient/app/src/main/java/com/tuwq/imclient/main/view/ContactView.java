package com.tuwq.imclient.main.view;

import java.util.List;

public interface ContactView {
    void onInitContact(List<String> contact);
    void onUpdateContact(List<String> contact,boolean isUpdateSuccess,String errorMsg);
    //void onUpdateContact(boolean isUpdateSuccess,String errorMsg);
}
