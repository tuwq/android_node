package com.tuwq.opennetlib.bean;

public class LoginBean {

    /**
     * msg : 成功收到信息
     * data : 11213ef2-8014-4877-ab1c-fd842178146f
     */

    private String msg;
    private String data;

    public LoginBean(String msg, String data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
