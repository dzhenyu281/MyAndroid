package com.dzhenyu.test.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/10/19.
 */
public class ImageScaleTypeFragment extends BaseFragment {

    private static List<View> views = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewPager viewPager = new ViewPager(mContext);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                             ViewGroup.LayoutParams.MATCH_PARENT));
        views.add(View.inflate(mContext, R.layout.image_scale_type_1, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_2, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_3, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_4, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_5, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_6, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_7, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_8, null));
        views.add(View.inflate(mContext, R.layout.image_scale_type_9, null));

        viewPager.setAdapter(new MyPagerAdapter());

        return viewPager;
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}
