package com.lulu.todayinhistory.mvp.model;

import com.lulu.todayinhistory.bean.DetialMessageInfo;
import com.lulu.todayinhistory.manager.ConstantUrl;
import com.lulu.todayinhistory.mvp.contract.DetialMessageContract;
import com.lulu.todayinhistory.net.NetUtils;
import com.lulu.todayinhistory.rx.RxSchedulers;
import com.lulu.todayinhistory.service.DetilMessageService;

import rx.Observable;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class DetialMessageModel implements DetialMessageContract.Model {
    @Override
    public Observable<DetialMessageInfo> detialMessage(String titleid,String appkey) {
        return NetUtils.getInstance()
                .getService(DetilMessageService.class, ConstantUrl.DETIALMESSAGE)
                .getDetial(titleid,appkey)
                .compose(RxSchedulers.<DetialMessageInfo>switchThread());
    }


}
