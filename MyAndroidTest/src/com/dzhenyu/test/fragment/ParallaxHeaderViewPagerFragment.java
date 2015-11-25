package com.dzhenyu.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dzhenyu.test.R;
import com.dzhenyu.test.ui.MyMapTab;


public class ParallaxHeaderViewPagerFragment extends BaseFragment
        implements MyMapTab.ScrollViewPagerListener, MyMapTab.MyMapTabPagerChangerListener {
    private LinearLayout llHeader;
    private static final String[] titles =
            new String[]{"实物兑换", "礼品", "兑换", "充值", "购物1", "购物2", "购物3"};
    private int rlHeaderHeight;
    private ViewPager mViewPager;
    private RelativeLayout rlHeader;
    private float lastPositionOffset = -1f;
    private boolean hasCheckOhterFregmentList;
    private MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parallax_header_layout, null);
        rlHeaderHeight = getResources().getDimensionPixelSize(R.dimen.rl_heander_height);

        llHeader = (LinearLayout) view.findViewById(R.id.ll_header);
        rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
        mViewPager = (ViewPager) view.findViewById(R.id.main_viewpager);
        adapter = new MyAdapter(getActivity().getSupportFragmentManager(), titles);
        mViewPager.setAdapter(adapter);
        MyMapTab myMapTab = (MyMapTab) view.findViewById(R.id.main_mymaptab);
        myMapTab.setViewPager(mViewPager);
        return view;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        int scroll;
        View v = view.getChildAt(0);
        if (v == null) {
            scroll = 0;
        }
        else if (firstVisibleItem >= 1) {
            scroll = rlHeaderHeight;
        }
        else {
            scroll = -v.getTop();
        }
        ViewCompat.setTranslationY(llHeader, Math.max(-scroll, -rlHeader.getHeight()));
    }

    @Override
    public void onTabPageSelected(int position) {
    }

    @Override
    public void onTabPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            hasCheckOhterFregmentList = false;
            lastPositionOffset = -1f;
        }
    }

    @Override
    public void onTabPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!hasCheckOhterFregmentList) {
            if (lastPositionOffset == -1f) {
                lastPositionOffset = positionOffset;
                return;
            }
            ParallaxHeaderListItemFragment curFragment = null;
            if (positionOffset - lastPositionOffset > 0) {
                curFragment = adapter.fragmentList.valueAt(position + 1);
            }
            else if (positionOffset - lastPositionOffset < 0) {
                curFragment = adapter.fragmentList.valueAt(position);
            }
            if (curFragment != null) {
                curFragment.setListPosition(ViewCompat.getTranslationY(llHeader));
                hasCheckOhterFregmentList = true;
            }
        }
    }

    class MyAdapter extends FragmentPagerAdapter {
        private String[] titles;

        public SparseArray<ParallaxHeaderListItemFragment> fragmentList = new SparseArray<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyAdapter(FragmentManager fm, String[] titles) {
            this(fm);
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            ParallaxHeaderListItemFragment fragment = new ParallaxHeaderListItemFragment(position);
            fragment.setOnScrollListener(ParallaxHeaderViewPagerFragment.this);
            if (fragmentList == null) {
                fragmentList = new SparseArray<>();
            }
            fragmentList.append(position, fragment);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
