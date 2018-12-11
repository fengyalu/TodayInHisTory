package com.lulu.todayinhistory.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lulu.todayinhistory.R;

import java.util.ArrayList;


/**
 * Created by fyl on 2018/3/5.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
  private   ArrayList<Fragment> list;
    private Context context;
    private String[] titles;
    private int[] images;
    private TabLayout tabLayout;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list, Context context, String[] titles, int[] images, TabLayout tabLayout) {
        super(fm);
        this.list = list;
        this.context=context;
        this.titles=titles;
        this.images=images;
        this.tabLayout=tabLayout;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab_menu, null);
        TextView textView = (TextView) v.findViewById(R.id.tv_title);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_icon);
        textView.setText(titles[position]);
        imageView.setImageResource(images[position]);
        textView.setTextColor(tabLayout.getTabTextColors());
        return v;
    }
}