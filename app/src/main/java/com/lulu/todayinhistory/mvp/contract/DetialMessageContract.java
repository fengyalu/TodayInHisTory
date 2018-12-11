package com.lulu.todayinhistory.mvp.contract;

import com.lulu.todayinhistory.base.BaseModel;
import com.lulu.todayinhistory.base.BasePresenter;
import com.lulu.todayinhistory.base.BaseView;
import com.lulu.todayinhistory.bean.DetialMessageInfo;
import com.lulu.todayinhistory.exception.ExceptionHandle;

import rx.Observable;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public interface DetialMessageContract {
    interface View extends BaseView {
        void onSucceed(DetialMessageInfo.ResultBeanX result);

        void onFail(ExceptionHandle.ResponeThrowable e);
    }

    interface Model extends BaseModel {
        Observable<DetialMessageInfo> detialMessage(String titleid,String appkey);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void detialMessage(String titleid);
    }
}
