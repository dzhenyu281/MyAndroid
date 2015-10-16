package com.dzhenyu.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dzhenyu.test.adapter.TextAdapter;
import com.dzhenyu.test.service.ScreenOnService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/9/22.
 */
public class ScreenOnFragment extends BaseFragment {

    private static int count = 0;
    private static final int HAS_MESSAGE = 1;

    private List<String> msgs = new ArrayList<>();
    private BaseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(mContext, ScreenOnService.class);
        mContext.startService(intent);
        Log.i("--->", "--->do on Create service");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_on_layout, null);
        adapter = new TextAdapter(mContext, msgs, TextAdapter.HEIGHT_TYPE_DEFAULT);
        ListView list = (ListView) view.findViewById(R.id.screen_on_list);
        list.setAdapter(adapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
