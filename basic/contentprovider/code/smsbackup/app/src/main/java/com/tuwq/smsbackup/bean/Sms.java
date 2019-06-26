package com.tuwq.smsbackup.bean;

public class Sms {

    public String date;
    public String address;
    public String body;
    @Override
    public String toString() {
        return "Sms [date=" + date + ", address=" + address + ", body=" + body
                + "]";
    }

}
