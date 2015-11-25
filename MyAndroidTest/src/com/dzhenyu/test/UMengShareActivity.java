package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.UMengShareFragment;

/**
 * Created by onlymem on 2015/11/4.
 */
public class UMengShareActivity extends BaseFragmentActivty{
    @Override
    protected BaseFragment createFragment() {
        return new UMengShareFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.share_title;
    }
}
