package com.dzhenyu.test;

/**
 * Created by onlymem on 2015/9/28.
 */
public class DrawableTestActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new DrawableTestFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.drawable_test_title;
    }
}
