package com.lulu.todayinhistory.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.bean.localdata.TNotes;
import com.lulu.todayinhistory.mvp.view.activity.NotListActivity;
import com.lulu.todayinhistory.mvp.view.activity.NoteDetialActivity;
import com.lulu.todayinhistory.utils.localdata.LocalDataDBManager;

import java.util.List;

/**
 * Created by fyl on 2018/12/3 0003.
 */

public class NotListAdapter extends BaseAdapter implements View.OnClickListener {
    // 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
    private int mScreentWidth;
    private View view;
    private Context context;
    private List<TNotes> list;

    public NotListAdapter(Context context, int mScreentWidth) {
        this.mScreentWidth = mScreentWidth;
        this.context = context;
    }


    public void setList(List<TNotes> list) {
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
        return position;
    }

    private boolean isClose = true;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // 如果没有设置过,初始化convertView
        if (convertView == null) {
            // 获得设置的view
            convertView = LayoutInflater.from(context).inflate(R.layout.item_not_list, parent, false);

            // 初始化holder
            holder = new ViewHolder();
            holder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);

            // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
            holder.dataTime = (TextView) convertView.findViewById(R.id.dataTime);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.delete = (Button) convertView.findViewById(R.id.delete);
            holder.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            LayoutParams lp = holder.content.getLayoutParams();
            lp.width = mScreentWidth;
            convertView.setTag(holder);
        } else {
            // 有直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
        holder.delete.setTag(position);
        holder.content.setTag(position);
        holder.ll_content.setTag(position);
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
        if (holder.hSView.getScrollX() != 0) {
            holder.hSView.scrollTo(0, 0);
        }

        // 设置背景颜色,设置填充内容.
        holder.dataTime.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        holder.content.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        holder.dataTime.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());

        // 设置监听事件
        holder.delete.setOnClickListener(this);
        holder.ll_content.setOnClickListener(this);
        return convertView;
    }

    /**
     * ViewHolder
     *
     * @Title:
     * @Description:主要是避免了不断的view获取初始化.
     * @Author:yzy
     * @Since:2013-10-22
     */
    class ViewHolder {
        public HorizontalScrollView hSView;
        public TextView dataTime;
        public TextView content;
        public View action;
        public Button delete;
        public LinearLayout ll_content;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.delete:
                boolean isDelete = LocalDataDBManager.getInstance(context).deleteTNotes(list.get(position));
                if (isDelete) {
                    list.remove(position);
                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ll_content:
                Intent intent=new Intent(context,NoteDetialActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("time",list.get(position).getDataTime());
                bundle.putString("content",list.get(position).getContent());
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;

            default:
                break;
        }
        // 刷新ListView内容
        notifyDataSetChanged();

    }
}
