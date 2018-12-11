package com.lulu.todayinhistory.bean;

import java.util.List;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class TitleMessageInfo {

    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"desc":"查询成功","status":"0","result":[{"year":"716","title":"唐睿宗李旦逝世","md5title":"4417124dc4b9e850fc098a8692fe094a","thumbnail_url":"http://www.todayonhistory.com/uploadfile/2016/0713/20160713051947902.jpg"},{"year":"-16","title":"汉成帝立赵飞燕为皇后","md5title":"ea2e618900b1c0ae5175e03af9217221","thumbnail_url":"http://www.todayonhistory.com/upic/201107/13/DD224346985.jpg"}]}
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
         * result : [{"year":"716","title":"唐睿宗李旦逝世","md5title":"4417124dc4b9e850fc098a8692fe094a","thumbnail_url":"http://www.todayonhistory.com/uploadfile/2016/0713/20160713051947902.jpg"},{"year":"-16","title":"汉成帝立赵飞燕为皇后","md5title":"ea2e618900b1c0ae5175e03af9217221","thumbnail_url":"http://www.todayonhistory.com/upic/201107/13/DD224346985.jpg"}]
         */

        private String desc;
        private String status;
        private List<ResultBean> result;

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

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * year : 716
             * title : 唐睿宗李旦逝世
             * titleid : 4417124dc4b9e850fc098a8692fe094a
             * thumbnail_url : http://www.todayonhistory.com/uploadfile/2016/0713/20160713051947902.jpg
             */

            private String year;
            private String title;
            private String titleid;
            private String thumbnail_url;

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitleid() {
                return titleid;
            }

            public void setTitleid(String titleid) {
                this.titleid = titleid;
            }

            public String getThumbnail_url() {
                return thumbnail_url;
            }

            public void setThumbnail_url(String thumbnail_url) {
                this.thumbnail_url = thumbnail_url;
            }
        }
    }
}
