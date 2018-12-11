package com.lulu.todayinhistory.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lulu.todayinhistory.R;
import com.lulu.todayinhistory.bean.localdata.TCollectMsg;

import java.util.List;

/**
 * Created by fyl on 2018/12/7 0007.
 */

public class DrawListAdapter extends BaseAdapter{

    private Context context;
    private List<TCollectMsg> list;
    public DrawListAdapter(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<TCollectMsg> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // 若无可重用的 view 则进行加载
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_collect_title_name, parent, false);
            // 初始化 ViewHolder 方便重用
            viewHolder = new ViewHolder();
            viewHolder.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.thumbnail_url);
            convertView.setTag(viewHolder);
        } else { // 否则进行重用
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ll_content.setTag(position);
        try {
            // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
            viewHolder.title.setText(list.get(position).getTitle());
            Uri uri = Uri.parse(list.get(position).getThumbnail_url());
            viewHolder.image.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    public static class ViewHolder {
        private LinearLayout ll_content;
        private TextView title;
        private SimpleDraweeView image;
    }
}
