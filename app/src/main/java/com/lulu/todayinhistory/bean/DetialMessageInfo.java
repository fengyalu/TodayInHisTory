package com.lulu.todayinhistory.bean;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class DetialMessageInfo {

    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"desc":"查询成功","status":"0","result":{"title":"汉成帝立赵飞燕为皇后","img_url":"http://www.todayonhistory.com/upic/201107/13/DD224346985.jpg","text":"赵飞燕(前1年)，原名    宜主，是西汉汉成帝的皇后和汉哀帝时的皇太后。赵飞燕是一位在中国历史上传奇的人物。在《汉书》中对她的描述仅仅只有少数几句，但关于她的野史却有许多。在中国民间和历史上，她以美貌著称，所谓;环肥燕瘦;讲的便是她和杨玉环，而燕瘦也通常用以比喻体态轻盈瘦弱的美女。"}}
     */

    private String code;
    private boolean charge;
    private String msg;
    private ResultBeanX result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        /**
         * desc : 查询成功
         * status : 0
         * result : {"title":"汉成帝立赵飞燕为皇后","img_url":"http://www.todayonhistory.com/upic/201107/13/DD224346985.jpg","text":"赵飞燕(前1年)，原名    宜主，是西汉汉成帝的皇后和汉哀帝时的皇太后。赵飞燕是一位在中国历史上传奇的人物。在《汉书》中对她的描述仅仅只有少数几句，但关于她的野史却有许多。在中国民间和历史上，她以美貌著称，所谓;环肥燕瘦;讲的便是她和杨玉环，而燕瘦也通常用以比喻体态轻盈瘦弱的美女。"}
         */

        private String desc;
        private String status;
        private ResultBean result;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * title : 汉成帝立赵飞燕为皇后
             * img_url : http://www.todayonhistory.com/upic/201107/13/DD224346985.jpg
             * text : 赵飞燕(前1年)，原名    宜主，是西汉汉成帝的皇后和汉哀帝时的皇太后。赵飞燕是一位在中国历史上传奇的人物。在《汉书》中对她的描述仅仅只有少数几句，但关于她的野史却有许多。在中国民间和历史上，她以美貌著称，所谓;环肥燕瘦;讲的便是她和杨玉环，而燕瘦也通常用以比喻体态轻盈瘦弱的美女。
             */

            private String title;
            private String img_url;
            private String text;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
