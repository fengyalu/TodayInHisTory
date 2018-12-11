package com.lulu.todayinhistory.mvp.model;

import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.manager.ConstantUrl;
import com.lulu.todayinhistory.mvp.contract.TitleMessageContract;
import com.lulu.todayinhistory.net.NetUtils;
import com.lulu.todayinhistory.rx.RxSchedulers;
import com.lulu.todayinhistory.service.TitleMessageService;

import rx.Observable;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class TitleMessageModel implements TitleMessageContract.Model {
    @Override
    public Observable<TitleMessageInfo> titleMessage(String str1,String str2) {
        return NetUtils.getInstance()
                .getService(TitleMessageService.class, ConstantUrl.TITLEMESSAGE)
                .getTitle(str1,str2)
                .compose(RxSchedulers.<TitleMessageInfo>switchThread());
    }
}
