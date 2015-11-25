package com.dzhenyu.test.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dzhenyu.test.DrawableShowActivity;
import com.dzhenyu.test.R;
import com.dzhenyu.test.adapter.TextAdapter;
import com.dzhenyu.test.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * drawable test
 */
public class DrawableTestFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView mlistView;
    private static List<String> drawablelist = new ArrayList<>();

    static {
        drawablelist.add("GradientDrawable");
        drawablelist.add("BitmapDrawable");
        drawablelist.add("NinePatchDrawable");
        drawablelist.add("InsetDrawable");
        drawablelist.add("ClipDrawable");
        drawablelist.add("ScaleDrawable");
        drawablelist.add("RotateDrawable");
        drawablelist.add("AnimationListDrawable");
        drawablelist.add("LayerDrawable");
        drawablelist.add("TransitionDrawable");
        drawablelist.add("LevelListDrawable");
        drawablelist.add("StateListDrawable");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawable_layout, null);
        mlistView = (ListView) view.findViewById(R.id.drawable_list);
        TextAdapter adapter = new TextAdapter(mContext, drawablelist, TextAdapter.HEIGHT_TYPE_DEFAULT);
        mlistView.setAdapter(adapter);
        mlistView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(mContext,DrawableShowActivity.class);
        intent.putExtra(DrawableShowActivity.DRAWABLE_SHOW_EXTRA,drawablelist.get(position));
        startActivity(intent);
    }
}
