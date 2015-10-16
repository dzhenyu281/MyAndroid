package com.dzhenyu.test;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dzhenyu.test.adapter.TextAdapter;
import com.dzhenyu.test.ui.MyMapTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/8/20.
 */
public class ParallaxHeaderListItemFragment extends Fragment implements AbsListView.OnScrollListener {

    private String strSign = "页面";

    private ListView listView;
    private Context mContext;
    private MyMapTab.ScrollViewPagerListener mScrollListener;

    public ParallaxHeaderListItemFragment() {

    }

    public ParallaxHeaderListItemFragment(int sign) {
        strSign += sign;
    }

    public void setOnScrollListener(MyMapTab.ScrollViewPagerListener listener) {
        this.mScrollListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parallax_header_item_layout, null);
        mContext = this.getActivity();
        listView = (ListView) view.findViewById(R.id.lv_listview);
        View headerView = new View(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.ll_heander_height));
        headerView.setLayoutParams(params);
        listView.addHeaderView(headerView);
        listView.setOnScrollListener(this);
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(strSign + " " + i);
        }
        listView.setAdapter(new TextAdapter(mContext, list, TextAdapter.HEIGHT_TYPE_DEFAULT));
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void setListPosition(float translationY) {
        Log.i("---->", "--->" + translationY);
        listView.setSelectionFromTop(1, (int) translationY);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
