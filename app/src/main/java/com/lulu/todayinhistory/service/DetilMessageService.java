package com.lulu.todayinhistory.service;

import com.lulu.todayinhistory.bean.DetialMessageInfo;
import com.lulu.todayinhistory.bean.TitleMessageInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public interface DetilMessageService {
    @FormUrlEncoded
    @POST("sjzh1004")
    Observable<DetialMessageInfo> getDetial(@Field("titleid") String qdate, @Query("appkey") String appkey);
}
