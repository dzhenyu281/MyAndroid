package com.dzhenyu.test;

/**
 * Created by onlymem on 2015/9/10.
 */
public class ParallaxHeaderViewPagerActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new ParallaxHeaderViewPagerFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.parallax_header_title;
    }
}
