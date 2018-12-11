package com.lulu.todayinhistory.bean.localdata;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2018/12/3 0003.
 */
@Entity
public class TNotes {
    @Id
    private String title;

    private String content;
    private String dataTime;

    @Generated(hash = 303561600)
    public TNotes(String title, String content, String dataTime) {
        this.title = title;
        this.content = content;
        this.dataTime = dataTime;
    }

    @Generated(hash = 485395687)
    public TNotes() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
