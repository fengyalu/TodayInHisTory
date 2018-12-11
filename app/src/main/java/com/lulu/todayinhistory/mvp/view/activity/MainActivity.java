package com.lulu.todayinhistory.mvp.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.adapter.DrawListAdapter;
import com.lulu.todayinhistory.adapter.TitleMessageAdapter;
import com.lulu.todayinhistory.base.BaseActivity;
import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.bean.localdata.TCollectMsg;
import com.lulu.todayinhistory.customview.SatelliteMenu;
import com.lulu.todayinhistory.exception.ExceptionHandle;
import com.lulu.todayinhistory.manager.ConstantEnvironment;
import com.lulu.todayinhistory.manager.UrlManager;
import com.lulu.todayinhistory.mvp.contract.TitleMessageContract;
import com.lulu.todayinhistory.mvp.presenter.TitleMessagePresenter;
import com.lulu.todayinhistory.utils.dialog.MyDialog;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<TitleMessagePresenter> implements TitleMessageContract.View, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private DrawListAdapter drawListAdapter;
    private String urlType = ConstantEnvironment.PRODUCTION;
    private ListView listView;
    private List<TCollectMsg> collectMsgList;
    private MyDialog myDialog;

    private ListView titleListView;
    private TitleMessageAdapter adapter;
    private List<TitleMessageInfo.ResultBeanX.ResultBean> resultList;
    private SatelliteMenu mSatelliteMenuRightTop;
    private DrawerLayout mDrawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translucentStatusBar();
        setContentView(R.layout.activity_main);
        UrlManager.getinstance().setProperties(urlType);
        initView();
        if (presenter != null) {
            presenter.titleMessage();
        }
        initClick();
    }

    private void initView() {
        drawListAdapter = new DrawListAdapter(this);
        adapter = new TitleMessageAdapter(this);
        myDialog = new MyDialog(this);
        listView = (ListView) findViewById(R.id.id_draw_menu_item_list_select);
        titleListView = (ListView) findViewById(R.id.listView);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.id_drawer_layout_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        //DrawerLayout监听器
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                inDrawerLayoutData();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        mSatelliteMenuRightTop = (SatelliteMenu) findViewById(R.id.mSatelliteMenuRightTop);

        List<Integer> imageResourceRightTop = new ArrayList<>();//菜单图片,可根据需要设置子菜单个数
        imageResourceRightTop.add(R.drawable.img_date);
        imageResourceRightTop.add(R.drawable.img_note);
        imageResourceRightTop.add(R.drawable.img_note_list);
        imageResourceRightTop.add(R.drawable.img_collect);
        imageResourceRightTop.add(R.drawable.img_introduce);

        List<String> menuItemName = new ArrayList<>();
        menuItemName.add("日期");
        menuItemName.add("心得");
        menuItemName.add("笔记");
        menuItemName.add("收藏");
        menuItemName.add("简介");

        mSatelliteMenuRightTop.getmBuilder()
                //.setMenuItemNameTexts(menuItemName)
                .setMenuImage(R.drawable.menu)
                .setMenuItemImageResource(imageResourceRightTop)
                .setOnMenuItemClickListener(new SatelliteMenu.OnMenuItemClickListener() {
                    @Override
                    public void onClick(View view, int postion) {
                        Toast.makeText(MainActivity.this, "点击了菜单:" + postion, Toast.LENGTH_LONG).show();
                        Intent intent=null;
                        switch (postion) {
                            case 0:
                                intent = new Intent(MainActivity.this, CalendarActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(MainActivity.this, NoteActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(MainActivity.this, NotListActivity.class);
                                startActivity(intent);
                                break;
                            case 3:
                                mDrawerlayout.openDrawer(Gravity.LEFT);
                                break;
                            case 4:
                                intent = new Intent(MainActivity.this, IntroduceActivity.class);
                                startActivity(intent);
                                break;
                            default:

                                break;
                        }
                    }
                })
                .creat();


    }

    private void inDrawerLayoutData() {
        collectMsgList = LocalDataDBManager.getInstance(this).queryAllCollectMsg();
        if (null != collectMsgList && !collectMsgList.isEmpty()) {
            drawListAdapter.setList(collectMsgList);
            listView.setAdapter(drawListAdapter);
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                myDialog.setTxtTitle("操作")
                        .setTxtBtnLeft("取消")
                        .setTxtBtnRight("确定")
                        .setTxtContent("确定取消收藏？")
                        .setCancelable(true)
                        .show(MyDialog.Type.TYPE_TWO, MyDialog.Gravitys.CENTER)
                        .setOnButtonClickListener(new MyDialog.OnButtonClickListener() {
                            @Override
                            public void onLeftClick() {
                                myDialog.dismiss();
                            }

                            @Override
                            public void onCenterClick() {
                                myDialog.dismiss();
                            }

                            @Override
                            public void onRightClick() {
                                TCollectMsg collectMsg = new TCollectMsg();
                                collectMsg.setTitle(collectMsgList.get(position).getTitle());
                                collectMsg.setTitleid(collectMsgList.get(position).getTitleid());
                                collectMsg.setThumbnail_url(collectMsgList.get(position).getThumbnail_url());
                                collectMsg.setYear(collectMsgList.get(position).getYear());
                                boolean isSave = LocalDataDBManager.getInstance(MainActivity.this).delCollectMsg(collectMsg);
                                if (isSave) {
                                    Toast.makeText(MainActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                }
                                drawListAdapter.setList(LocalDataDBManager.getInstance(MainActivity.this).queryAllCollectMsg());
                                listView.setAdapter(drawListAdapter);
                                myDialog.dismiss();
                            }
                        });

                return false;
            }
        });
    }

    /**
     * 实现5.0以上状态栏透明(默认状态是半透明)
     */
    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.colorPrimary
                    )
            );
        }

    }


    /**
     * 当使用setSupportActionBar()时,调用Toolbar.inflateMenu()时无效果,必须用此方法实现
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.draw_menu_layout, menu);
        return true;
    }

    @Override
    protected TitleMessagePresenter onCreatePresenter() {
        return new TitleMessagePresenter(this);
    }

    @Override
    public void onSucceed(TitleMessageInfo.ResultBeanX result) {
        if (result != null) {
            resultList = result.getResult();
            adapter.setList(resultList);
            titleListView.setAdapter(adapter);
        }
    }

    @Override
    public void onFail(ExceptionHandle.ResponeThrowable e) {

    }

    private void initClick() {
        titleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != resultList && !resultList.isEmpty()) {
                    try {
                        Intent intent = new Intent(MainActivity.this, DetialMessageActivity.class);
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
    public void onClick(View v) {

    }
}
