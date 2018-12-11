package com.lulu.todayinhistory.mvp.contract;

import com.lulu.todayinhistory.base.BaseModel;
import com.lulu.todayinhistory.base.BasePresenter;
import com.lulu.todayinhistory.base.BaseView;
import com.lulu.todayinhistory.bean.DetialMessageInfo;
import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.exception.ExceptionHandle;

import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public interface TitleMessageContract {
    interface View extends BaseView {
        void onSucceed(TitleMessageInfo.ResultBeanX result);

        void onFail(ExceptionHandle.ResponeThrowable e);
    }

    interface Model extends BaseModel {
        Observable<TitleMessageInfo> titleMessage(String str1,String str2);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void titleMessage();
    }
}
