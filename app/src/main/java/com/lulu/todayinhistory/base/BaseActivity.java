package com.lulu.todayinhistory.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lulu.todayinhistory.R;

public abstract class BaseActivity <Presenter extends BasePresenter> extends AppCompatActivity {

    protected Presenter presenter;
    protected abstract Presenter onCreatePresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_base);
        if (onCreatePresenter()!=null){
            presenter=onCreatePresenter();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.unsubscribe();
        }
    }
}
