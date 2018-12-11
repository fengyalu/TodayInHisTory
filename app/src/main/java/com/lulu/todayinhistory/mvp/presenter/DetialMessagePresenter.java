package com.lulu.todayinhistory.mvp.presenter;

import com.lulu.todayinhistory.bean.DetialMessageInfo;
import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.exception.ExceptionHandle;
import com.lulu.todayinhistory.mvp.contract.DetialMessageContract;
import com.lulu.todayinhistory.mvp.contract.TitleMessageContract;
import com.lulu.todayinhistory.mvp.model.DetialMessageModel;
import com.lulu.todayinhistory.mvp.model.TitleMessageModel;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class DetialMessagePresenter extends DetialMessageContract.Presenter {
    private DetialMessageContract.View view;
    private final DetialMessageContract.Model model;

    public DetialMessagePresenter(DetialMessageContract.View view) {
        this.view = view;
        model = new DetialMessageModel();
    }

    @Override
    public void detialMessage(String titleid) {
        Subscription subscription= null;
        String appkey = "a6f23f119ae097bc8a039e09fb8702e4";
        try {
            subscription = model.detialMessage(titleid,appkey)
                    .subscribe(new Subscriber<DetialMessageInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onFail(ExceptionHandle.handleException(e));
                        }

                        @Override
                        public void onNext(DetialMessageInfo detialMessageInfo) {
                            if ("10000".equals(detialMessageInfo.getCode())){
                                view.onSucceed(detialMessageInfo.getResult());
                            }else {
                                view.onFail(ExceptionHandle.handleErrorMsg(detialMessageInfo));
                            }
                        }
                    });
            subscribe(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
