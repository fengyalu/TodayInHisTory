package com.lulu.todayinhistory.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.bean.TitleMessageInfo;
import com.lulu.todayinhistory.bean.localdata.TCollectMsg;
import com.lulu.todayinhistory.mvp.view.activity.DetialMessageActivity;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.util.List;

/**
 * Created by fyl on 2018/11/29 0029.
 */

public class TitleMessageAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<TitleMessageInfo.ResultBeanX.ResultBean> list;
    private View view;

    public TitleMessageAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<TitleMessageInfo.ResultBeanX.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private boolean isClose = true;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // 若无可重用的 view 则进行加载
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_title_name, parent, false);
            // 初始化 ViewHolder 方便重用
            viewHolder = new ViewHolder();
            viewHolder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
            viewHolder.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
             viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.thumbnail_url);
             viewHolder.year = (TextView) convertView.findViewById(R.id.year);
             viewHolder.collect = (Button) convertView.findViewById(R.id.collect);
             viewHolder.share = (Button) convertView.findViewById(R.id.share);
            convertView.setTag(viewHolder);
        } else { // 否则进行重用
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.collect.setTag(position);
        viewHolder.share.setTag(position);
        viewHolder.ll_content.setTag(position);
        //设置监听事件
        convertView.setOnTouchListener(new View.OnTouchListener() {

            private int y;
            private int x;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (view != null) {
                            ViewHolder viewHolder1 = (ViewHolder) view.getTag();
                            viewHolder1.hSView.smoothScrollTo(0, 0);

                        }
                        x = (int) event.getX();
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x3 = (int) event.getX();
                        int y3 = (int) event.getY();
                        int dY = Math.abs(y - y3);
                        int dx = Math.abs(x - x3);
                        if (dx > dY && dx > 20) {
                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        // 获得ViewHolder
                        ViewHolder viewHolder = (ViewHolder) v.getTag();
                        view = v;
                        // 获得HorizontalScrollView滑动的水平方向值.
                        int scrollX = viewHolder.hSView.getScrollX();
                        // 获得操作区域的长度
                        int actionW = viewHolder.action.getWidth();

                        // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                        // 如果水平方向的移动值<操作区域的长度的一半,就复原
                        if (isClose) {
                            if (scrollX < (actionW / 5)) {
                                isClose = true;
                                viewHolder.hSView.smoothScrollTo(0, 0);
                            } else// 否则的话显示操作区域
                            {
                                isClose = false;
                                viewHolder.hSView.smoothScrollTo(actionW, 0);
                            }

                        } else {

                            if (scrollX < (actionW * 4.0 / 5.0)) {
                                isClose = true;
                                viewHolder.hSView.smoothScrollTo(0, 0);
                            } else// 否则的话显示操作区域
                            {
                                isClose = false;
                                viewHolder.hSView.smoothScrollTo(actionW, 0);
                            }
                        }
                        return true;
                }
                return false;
            }
        });

        // 这里防止删除一条item后,ListView处于操作状态,直接还原
        if (viewHolder.hSView.getScrollX() != 0) {
            viewHolder.hSView.scrollTo(0, 0);
        }


        // 设置监听事件
        viewHolder.collect.setOnClickListener(this);
        viewHolder.share.setOnClickListener(this);
        viewHolder.ll_content.setOnClickListener(this);
        try {
            // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
            viewHolder.title.setText(list.get(position).getTitle());
            Uri uri = Uri.parse(list.get(position).getThumbnail_url());
            viewHolder.image.setImageURI(uri);
            viewHolder.year.setText(list.get(position).getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.collect:
                TCollectMsg collectMsg=new TCollectMsg();
                collectMsg.setTitle(list.get(position).getTitle());
                collectMsg.setTitleid(list.get(position).getTitleid());
                collectMsg.setThumbnail_url(list.get(position).getThumbnail_url());
                collectMsg.setYear(list.get(position).getYear());
                boolean isSave = LocalDataDBManager.getInstance(context).saveCollectMsg(collectMsg);
                if (isSave){
                    Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.share:

                break;
            case R.id.ll_content:
                try {
                    Intent intent = new Intent(context, DetialMessageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("titleid", list.get(position).getTitleid());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
        // 刷新ListView内容
        notifyDataSetChanged();

    }

    public static class ViewHolder {
        public HorizontalScrollView hSView;
        private LinearLayout ll_content;
        private TextView title,year;
        private SimpleDraweeView image;
        private Button collect,share;
        public View action;
    }
}
