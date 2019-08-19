package com.tuwq.quickindex;

public class Friend implements Comparable<Friend>{
    public String pinyin;
    public String name;
    public Friend(String name) {
        this.name = name;

        pinyin = PinYinUtil.getPinYin(this.name);
    }

    @Override
    public int compareTo(Friend o) {
        return this.pinyin.compareTo(o.pinyin);
    }
}
