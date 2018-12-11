package com.lulu.todayinhistory.mvp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.adapter.NotListAdapter;
import com.lulu.todayinhistory.bean.localdata.TNotes;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.util.List;

public class NotListActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "NotListActivity";

    private ListView listView;
    private DisplayMetrics dm;
    private NotListAdapter notListAdapter;
    private Gson gson;
    private List<TNotes> notesList;
    private RelativeLayout title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_list);
        initView();
        initData();
        initAdapter();
    }

    private void initView() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        notListAdapter = new NotListAdapter(this, dm.widthPixels);
        listView = (ListView)findViewById(R.id.listView);
        title = (RelativeLayout)findViewById(R.id.title);
        title.setOnClickListener(this);
        gson = new Gson();
    }

    private void initData() {
        notesList = LocalDataDBManager.getInstance(this).queryAllNotes();
    }

    private void initAdapter() {
        if (null!=notesList&&!notesList.isEmpty()){
            notListAdapter.setList(notesList);
            listView.setAdapter(notListAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title:
                finish();
                break;

            default:
                break;
        }
    }
}
