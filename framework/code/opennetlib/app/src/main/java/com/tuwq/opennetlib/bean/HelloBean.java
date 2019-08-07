package com.tuwq.opennetlib.bean;

public class HelloBean {

    /**
     * msg : 成功收到信息
     * data : {"name":"xx"}
     */

    private String msg;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HelloBean{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * name : xx
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}

