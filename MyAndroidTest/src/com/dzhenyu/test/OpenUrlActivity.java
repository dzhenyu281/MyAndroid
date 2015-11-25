package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.OpenUrlFragment;

/**
 * Created by onlymem on 2015/10/20.
 */
public class OpenUrlActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new OpenUrlFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.open_url_title;
    }
}
