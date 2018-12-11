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

public interface TitleMessageService {
    @FormUrlEncoded
    @POST("sjzh1003")
    //Observable<TitleMessageInfo> getTitle(@Query("qdate") String data,@Query("appkey") String data2);
    Observable<TitleMessageInfo> getTitle(@Field("qdate") String qdate,@Query("appkey") String appkey);
}
