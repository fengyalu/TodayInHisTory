package com.lulu.todayinhistory.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.bean.localdata.TNotes;
import com.lulu.todayinhistory.customview.MyTextEditext;
import com.lulu.todayinhistory.customview.SatelliteMenu;
import com.lulu.todayinhistory.utils.dialog.MyDialog;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "NoteActivity";

    private MyTextEditext editext;
    private ImageView btnMenu;
    private String time;
    private PopupMenu pop;
    private RelativeLayout rlTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initView();
    }

    private void initView() {
        editext = (MyTextEditext) findViewById(R.id.mytxt);
        btnMenu = (ImageView) findViewById(R.id.btn_menu);
        rlTitle = (RelativeLayout) findViewById(R.id.title);
        rlTitle.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                pop = new PopupMenu(this, v);
                try {
                    Field field = pop.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    MenuPopupHelper helper = (MenuPopupHelper) field.get(pop);
                    helper.setForceShowIcon(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pop.getMenuInflater().inflate(R.menu.menu_notes, pop.getMenu());
                pop.show();
                pop.setOnMenuItemClickListener(this);
                break;
            case R.id.title:
                finish();
                break;
            default:
                break;
        }
    }

    private String dataTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间     
        String time = formatter.format(curDate);
        return time;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            String content = editext.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                //保存
                time = dataTime();
                TNotes tNotes = new TNotes();
                tNotes.setTitle(time);
                tNotes.setContent(content);
                tNotes.setDataTime(time);
                LocalDataDBManager.getInstance(this).saveNotes(tNotes);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                //保存
                String content = editext.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    TNotes tNotes = new TNotes();
                    tNotes.setTitle(time);
                    tNotes.setContent(content);
                    tNotes.setDataTime(time);
                    boolean isSave = LocalDataDBManager.getInstance(NoteActivity.this).saveNotes(tNotes);
                    if (isSave) {
                        Toast.makeText(NoteActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.search:
                //查询
                List<TNotes> noteList = LocalDataDBManager.getInstance(NoteActivity.this).queryAllNotes();
                if (null != noteList && !noteList.isEmpty()) {
                    Intent intent = new Intent(NoteActivity.this, NotListActivity.class);
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String noteJson = gson.toJson(noteList);
                    bundle.putString("noteJson", noteJson);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
        return false;
    }
}
