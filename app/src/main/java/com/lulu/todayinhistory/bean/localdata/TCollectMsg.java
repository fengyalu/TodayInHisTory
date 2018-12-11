package com.lulu.todayinhistory.bean.localdata;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by fyl on 2018/12/4 0004.
 */

@Entity
public class TCollectMsg {
    @Id
    private String titleid;

    private String year;
    private String title;
    private String thumbnail_url;

    @Generated(hash = 1394497489)
    public TCollectMsg(String titleid, String year, String title, String thumbnail_url) {
        this.titleid = titleid;
        this.year = year;
        this.title = title;
        this.thumbnail_url = thumbnail_url;
    }

    @Generated(hash = 1283055896)
    public TCollectMsg() {
    }

    public String getTitleid() {
        return this.titleid;
    }

    public void setTitleid(String titleid) {
        this.titleid = titleid;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail_url() {
        return this.thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
}
