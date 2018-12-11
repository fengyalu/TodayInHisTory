package com.lulu.todayinhistory.mvp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.adapter.NotListAdapter;
import com.lulu.todayinhistory.bean.localdata.TNotes;
import com.lulu.todayinhistory.customview.MyTextEditext;
import com.lulu.todayinhistory.customview.SatelliteMenu;
import com.lulu.todayinhistory.mvp.view.activity.NotListActivity;
import com.lulu.todayinhistory.utils.dialog.MyDialog;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements View.OnClickListener {
    private MyTextEditext editext;
    private MyTextEditext myText;
    private ImageButton btnMenu;
    private MyDialog myDialog;
    private NotListAdapter notListAdapter;
    private String time;
    private TextView showTime;

    private SatelliteMenu mSatelliteMenuLeftTop, mSatelliteMenuRightTop, mSatelliteMenuRightBottom, mSatelliteMenuLeftBottom;
    private List<Integer> imageResourceLeftTop;
    private List<String> nameMenuItem;

    public NoteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        String time = showTime();
        showTime.setText(time);
        List<Integer> imageResourceRightTop = new ArrayList<>();//菜单图片,可根据需要设置子菜单个数
        imageResourceRightTop.add(R.drawable.imag_msg);
        imageResourceRightTop.add(R.drawable.imag_music);
        imageResourceRightTop.add(R.drawable.imag_pic);
        imageResourceRightTop.add(R.drawable.imag_take_photo);
        imageResourceRightTop.add(R.drawable.imag_tel);

        List<String> menuItemName=new ArrayList<>();
        menuItemName.add("查看");
        menuItemName.add("保存");

        mSatelliteMenuRightTop.getmBuilder()
                .setMenuImage(R.drawable.menu)
                .setMenuItemImageResource(imageResourceRightTop)
                //.setMenuItemNameTexts()
                .setOnMenuItemClickListener(new SatelliteMenu.OnMenuItemClickListener() {
                    @Override
                    public void onClick(View view, int postion) {
                        Toast.makeText(getActivity(), "点击了菜单:" + postion, Toast.LENGTH_LONG).show();
                    }
                })
                .creat();


        return view;
    }

    private void initView(View view) {
        myText = new MyTextEditext(getActivity());
        myDialog = new MyDialog(getActivity());
        showTime = (TextView) view.findViewById(R.id.dataTime);
        editext = (MyTextEditext) view.findViewById(R.id.mytxt);
        btnMenu = (ImageButton) view.findViewById(R.id.btn_menu);

        mSatelliteMenuRightTop= (SatelliteMenu) view.findViewById(R.id.mSatelliteMenuRightTop);
        btnMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:
                time = dataTime();
                myDialog.setTxtTitle("操作")
                        .setTxtBtnLeft("查看")
                        .setTxtBtnRight("保存")
                        .setCancelable(true)
                        .show(MyDialog.Type.TYPE_TWO, MyDialog.Gravitys.CENTER)
                        .setOnButtonClickListener(new MyDialog.OnButtonClickListener() {
                            @Override
                            public void onLeftClick() {
                                //查询
                                List<TNotes> noteList = LocalDataDBManager.getInstance(getActivity()).queryAllNotes();
                                if (null != noteList && !noteList.isEmpty()) {
                                    Intent intent = new Intent(getActivity(), NotListActivity.class);
                                    Bundle bundle = new Bundle();
                                    Gson gson = new Gson();
                                    String noteJson = gson.toJson(noteList);
                                    bundle.putString("noteJson", noteJson);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                                myDialog.dismiss();
                            }

                            @Override
                            public void onCenterClick() {
                                myDialog.dismiss();
                            }

                            @Override
                            public void onRightClick() {
                                //保存
                                String content = editext.getText().toString();
                                if (!TextUtils.isEmpty(content)) {
                                    TNotes tNotes = new TNotes();
                                    tNotes.setTitle(time);
                                    tNotes.setContent(content);
                                    tNotes.setDataTime(time);
                                    boolean isSave = LocalDataDBManager.getInstance(getActivity()).saveNotes(tNotes);
                                    if (isSave) {
                                        Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                myDialog.dismiss();
                            }
                        });
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

    private String showTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间     
        String time = formatter.format(curDate);
        return time;
    }


    //退出时自动保存
    public void onKeyDownChild(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            final String content = editext.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                //保存
                time = dataTime();
                TNotes tNotes = new TNotes();
                tNotes.setTitle(time);
                tNotes.setContent(content);
                tNotes.setDataTime(time);
               LocalDataDBManager.getInstance(getActivity()).saveNotes(tNotes);
            }
        }
    }
}

