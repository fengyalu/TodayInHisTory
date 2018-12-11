package com.lulu.todayinhistory.mvp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lulu.todayinhistory.R;

public class NoteDetialActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "NoteDetialActivity";

    private TextView content;
    private TextView noteTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detial);
        initView();
        initData();
    }

    private void initView() {
        content= (TextView) findViewById(R.id.content);
        noteTxt = (TextView) findViewById(R.id.note_txt);
        RelativeLayout title = (RelativeLayout) findViewById(R.id.title);
        title.setOnClickListener(this);
    }

    private void initData() {
        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        String time = bundle.getString("time");
        String contentText = bundle.getString("content");
        noteTxt.setText("笔记"+"("+time+")");
        content.setText(contentText);
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
