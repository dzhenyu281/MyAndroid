package com.dzhenyu.test;

/**
 * Created by onlymem on 2015/9/22.
 */
public class ScreenOnActivity extends  BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new ScreenOnFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.screen_on_title;
    }
}
