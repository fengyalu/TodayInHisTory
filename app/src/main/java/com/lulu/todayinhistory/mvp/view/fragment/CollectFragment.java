package com.lulu.todayinhistory.mvp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulu.todayinhistory.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends Fragment {


    public CollectFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collect, container, false);
    }

}
