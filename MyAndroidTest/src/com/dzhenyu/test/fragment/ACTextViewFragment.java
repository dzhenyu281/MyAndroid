package com.dzhenyu.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.dzhenyu.test.R;
import com.dzhenyu.test.adapter.MyListAdapter;
import com.dzhenyu.test.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/10/20.
 */
public class ACTextViewFragment extends BaseFragment {

    private static List<String> strRescourse = new ArrayList<>();

    static {
        strRescourse.add("beijing1");
        strRescourse.add("beijing2");
        strRescourse.add("beijing3");
        strRescourse.add("上海1");
        strRescourse.add("上海2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_textview_layout, container, false);
        AutoCompleteTextView acText = (AutoCompleteTextView) view.findViewById(R.id.act_textview_1);
        MultiAutoCompleteTextView macText = (MultiAutoCompleteTextView) view.findViewById(R.id.mact_textview_2);

        TextAdapter adapter = new TextAdapter(mContext, strRescourse);
//        ArrayAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strRescourse);
        acText.setAdapter(adapter);

        macText.setAdapter(adapter);
        macText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        view.findViewById(R.id.tv_textview_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
            }
        });

        return view;
    }
}
