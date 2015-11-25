package com.dzhenyu.test.function.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dzhenyu.test.R;
import com.dzhenyu.test.adapter.TextAdapter;
import com.dzhenyu.test.fragment.BaseFragment;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by onlymem on 2015/11/12.
 */
public class SimpleAnimationFragment extends BaseFragment implements
        AdapterView.OnItemClickListener {

    private ListView listView;
    private LinkedList<String> animationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simple_animation_layout, container, false);
        listView = (ListView) view.findViewById(R.id.simple_animation_list);
        initDate(AnimationContant.SIMPLE_ANIMATION);
        listView.setAdapter(new TextAdapter(mContext, animationList));
        listView.setOnItemClickListener(this);
        return view;
    }

    private void initDate(Map<String, Integer> map) {
        animationList = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            animationList.add(entry.getKey());
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (position == 3) {
//            AnimationSet set = new AnimationSet(true);
//            ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, 0.5f, 0.5f);
//            scaleAnimation1.setDuration(1000);
//            scaleAnimation1.setInterpolator(new AccelerateDecelerateInterpolator());
//            ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f);
//            scaleAnimation2.setDuration(1000);
//            scaleAnimation2.setStartOffset(1000);
//            scaleAnimation2.setInterpolator(new AccelerateDecelerateInterpolator());
//            set.addAnimation(scaleAnimation1);
////            set.addAnimation(scaleAnimation2);
//            listView.startAnimation(set);
//
//        } else {
            Animation animation = AnimationUtils.loadAnimation(mContext,
                                                               AnimationContant.SIMPLE_ANIMATION
                                                                       .get(animationList
                                                                                    .get(position)));
            listView.startAnimation(animation);
//        }
    }
}
