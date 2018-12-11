package com.lulu.todayinhistory.mvp.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.base.BaseActivity;
import com.lulu.todayinhistory.bean.DetialMessageInfo;
import com.lulu.todayinhistory.exception.ExceptionHandle;
import com.lulu.todayinhistory.mvp.contract.DetialMessageContract;
import com.lulu.todayinhistory.mvp.presenter.DetialMessagePresenter;

import java.util.List;

public class DetialMessageActivity extends BaseActivity<DetialMessageContract.Presenter> implements DetialMessageContract.View,View.OnClickListener{

    private TextView title;
    private SimpleDraweeView imgDetial;
    private TextView txtDetial;
    private Gson gson;
    private List<String> urlList;
    private WebView webView;

    @Override
    protected DetialMessageContract.Presenter onCreatePresenter() {
        return new DetialMessagePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detial_message);
        initView();
        initData();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.detial_title);
        imgDetial = (SimpleDraweeView) findViewById(R.id.img_detial);
        txtDetial = (TextView) findViewById(R.id.txt_detial);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        gson = new Gson();
        RelativeLayout title = (RelativeLayout) findViewById(R.id.title);
        title.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String titleid = bundle.getString("titleid");
        if (!TextUtils.isEmpty(titleid)) {
            presenter.detialMessage(titleid);
        }
    }

    @Override
    public void onSucceed(DetialMessageInfo.ResultBeanX result) {
        Uri uri=null;
        try {
            title.setText(result.getResult().getTitle());
            String url = result.getResult().getImg_url();
            urlList = gson.fromJson(url, new TypeToken<List<String>>() {
            }.getType());
            if (null != urlList && !urlList.isEmpty()) {
                uri = Uri.parse(urlList.get(0));
            }
            imgDetial.setImageURI(uri);
            String special_content = result.getResult().getText();
            txtDetial.setText(Html.fromHtml(result.getResult().getText()));
            special_content = special_content.replaceAll("&", "");
            //special_content = special_content.replaceAll(""","\"");
            special_content = special_content.replaceAll("<", "<");
            special_content = special_content.replaceAll(">", ">");
            special_content = special_content.replaceAll("rdquo;", "”");
            special_content = special_content.replaceAll("mdash;", "-");
            special_content = special_content.replaceAll("ldquo;", "“");
            special_content = special_content.replaceAll("\\n", "<br>");//换行
            special_content = special_content.replaceAll("<img", "<img width=\"100%\"");//图片不超出屏幕
            webView.loadDataWithBaseURL(null,special_content , "text/html", "utf-8", null);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(ExceptionHandle.ResponeThrowable e) {

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
