package com.dzhenyu.test.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dzhenyu.test.R;
import com.dzhenyu.test.adapter.TextAdapter;
import com.dzhenyu.test.function.animation.LayoutAnimatorActivity;
import com.dzhenyu.test.function.animation.PropertyAnimationActivity;
import com.dzhenyu.test.function.animation.SearchFlyActivity;
import com.dzhenyu.test.function.animation.SimpleAnimationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/11/9.
 */
public class ImitationAnimationFragment extends BaseFragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imiation_animation_layout, container, false);
        ListView mlistView = (ListView) view.findViewById(R.id.list_imitation_animation);
        mlistView.setAdapter(
                new TextAdapter(mContext, ImitationAnimationContent.IMITATION_ANIMATION_ARRAY));

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intint = null;
                switch (position) {
                    case 0:
                        intint = new Intent(mContext, SimpleAnimationActivity.class);
                        break;
                    case 2:
                        intint = new Intent(mContext, PropertyAnimationActivity.class);
                        break;
                    case 3:
                        intint = new Intent(mContext, SearchFlyActivity.class);
                        break;
                    case 4:
                        intint = new Intent(mContext, LayoutAnimatorActivity.class);
                        break;
                    default:
                        break;
                }
                if (intint != null) {
                    startActivity(intint);
                }
            }
        });
        return view;
    }

    public static class ImitationAnimationContent {

        public static final List<String> IMITATION_ANIMATION_ARRAY = new ArrayList<>();

        static {
            IMITATION_ANIMATION_ARRAY.add("简单动画");
            IMITATION_ANIMATION_ARRAY.add("火车票出票动画");
            IMITATION_ANIMATION_ARRAY.add("属性动画练习");
            IMITATION_ANIMATION_ARRAY.add("文字飞入飞出");
            IMITATION_ANIMATION_ARRAY.add("LayoutAnimation");

        }
    }

}
