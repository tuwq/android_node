package com.tuwq.zhbj.bean;

import java.util.List;

public class NewBean {
    public NewItem data;
    public String retcode;

    public class NewItem{
        public String countcommenturl;
        public String more;
        public String title;
        public List<News> news;
        public List<TopPic> topic;
        public List<TopNews> topnews;
    }
    public class News{
        public boolean comment;
        public String commentlist;
        public String commenturl;
        //
        public String id;
        //
        public String listimage;
        //
        public String pubdate;
        //
        public String title;
        public String type;

        //
        public String url;

        //
        public boolean isRead;
    }
    public class TopPic{
        public String description;
        public int id;
        public String listimage;
        public String sort;
        public String title;
        public String url;
    }
    public class TopNews{
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public int id;
        public String pubdate;
        //
        public String title;
        //
        public String topimage;
        public String type;
        //
        public String url;
    }
}

