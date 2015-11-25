package com.dzhenyu.test.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.dzhenyu.test.R;

/**
 * Created by onlymem on 2015/10/20.
 */
public class MyListAdapter extends ArrayAdapter {


    public MyListAdapter(Context context, int resource) {
        super(context, resource);
    }
}
