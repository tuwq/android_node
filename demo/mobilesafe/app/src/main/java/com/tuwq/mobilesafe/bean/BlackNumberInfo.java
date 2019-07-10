package com.tuwq.mobilesafe.bean;

public class BlackNumberInfo {
    public String blacknumber;
    public int mode;

    public BlackNumberInfo(String blacknumber, int mode) {
        super();
        this.blacknumber = blacknumber;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BlackNumberInfo [blacknumber=" + blacknumber + ", mode=" + mode
                + "]";
    }
}
