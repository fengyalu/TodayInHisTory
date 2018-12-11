package com.lulu.todayinhistory.mvp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.adapter.TitleMessageAdapter;
import com.lulu.todayinhistory.base.BaseFragment;
import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.bean.localdata.TNotes;
import com.lulu.todayinhistory.exception.ExceptionHandle;
import com.lulu.todayinhistory.mvp.contract.TitleMessageContract;
import com.lulu.todayinhistory.mvp.presenter.TitleMessagePresenter;
import com.lulu.todayinhistory.mvp.view.activity.DetialMessageActivity;
import com.lulu.todayinhistory.utils.dialog.MyDialog;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends BaseFragment<TitleMessagePresenter> implements TitleMessageContract.View {
    private static final String TAG = "TodayFragment";

    private ListView listView;
    private TitleMessageAdapter adapter;
    private List<TitleMessageInfo.ResultBeanX.ResultBean> resultList;

    public TodayFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        initView(view);
        if (presenter!=null) {
            presenter.titleMessage();
        }
        initClick();
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new TitleMessageAdapter(getActivity());
    }

    private void initClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null!=resultList&&!resultList.isEmpty()) {
                    try {
                        Intent intent = new Intent(getActivity(), DetialMessageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("titleid", resultList.get(position).getTitleid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    @Override
    protected TitleMessagePresenter onCreatePresenter() {
        return new TitleMessagePresenter(this);
    }

    @Override
    public void onSucceed(TitleMessageInfo.ResultBeanX result) {
        if (result!=null){
            resultList = result.getResult();
            adapter.setList(resultList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onFail(ExceptionHandle.ResponeThrowable e) {

    }
}
