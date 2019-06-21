package com.tuwq.xmlserialization;

public class SMS {
    // 发信人
    public String from;
    // 短信内容
    public String content;
    // 收到短信的时间
    public String time;

    @Override
    public String toString() {
        return "SMS{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
