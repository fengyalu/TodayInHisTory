package com.lulu.todayinhistory.mvp.presenter;

import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.exception.ExceptionHandle;
import com.lulu.todayinhistory.mvp.contract.TitleMessageContract;
import com.lulu.todayinhistory.mvp.model.TitleMessageModel;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class TitleMessagePresenter extends TitleMessageContract.Presenter {
    private TitleMessageContract.View view;
    private final TitleMessageContract.Model model;

    public TitleMessagePresenter(TitleMessageContract.View view) {
        this.view = view;
        model = new TitleMessageModel();
    }
    @Override
    public void titleMessage() {
        Subscription subscription= null;
        String str1 = "",str2 = "a6f23f119ae097bc8a039e09fb8702e4";
        try {
            subscription = model.titleMessage(str1,str2)
                    .subscribe(new Subscriber<TitleMessageInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onFail(ExceptionHandle.handleException(e));
                        }

                        @Override
                        public void onNext(TitleMessageInfo titleMessage) {
                            if ("10000".equals(titleMessage.getCode())){
                                view.onSucceed(titleMessage.getResult());
                            }else {
                                view.onFail(ExceptionHandle.handleErrorMsg(titleMessage));
                            }
                        }
                    });
            subscribe(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
