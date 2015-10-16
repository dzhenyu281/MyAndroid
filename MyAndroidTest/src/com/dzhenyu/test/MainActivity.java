package com.dzhenyu.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dzhenyu.test.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by onlymem on 2015/8/25.
 */

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<String> mTitleList;
    private List<String> mClassList;
    private Context context;
    private TextAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layont);
        context = this;
        ListView listView = (ListView) findViewById(R.id.main_list);
        initData();

        if (mTitleList != null) {
            listView.setAdapter(new TextAdapter(context, mTitleList, TextAdapter.HEIGHT_TYPE_DEFAULT));
            listView.setOnItemClickListener(this);
        }
    }

    private void initData() {
        mTitleList = new ArrayList<>();
        Collections.addAll(mTitleList, this.getResources().getStringArray(R.array.main_list_title));
        mClassList = new ArrayList<>();
        Collections.addAll(mClassList, this.getResources().getStringArray(R.array.main_list_class));

        if (mClassList.size() != mTitleList.size()) {
            throw new IllegalStateException("Resources main_list_title and main_list_class size not same");
        }

        setActivityTitle(R.string.main_title);
        setBtnBackVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String classPath = mClassList.get(position);
        startActivity(classPath);
    }

    private void startActivity(String classPath) {
        if (TextUtils.isEmpty(classPath)) {
            return;
        }
        try {
            Class objectClass = Class.forName(classPath);
            Intent intent = new Intent(context, objectClass);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, R.string.not_found_class, Toast.LENGTH_SHORT).show();
        }
    }
}
