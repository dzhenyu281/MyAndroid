package com.dzhenyu.test;

import com.dzhenyu.test.fragment.ACTextViewFragment;
import com.dzhenyu.test.fragment.BaseFragment;

/**
 * Created by onlymem on 2015/10/20.
 */
public class ACTextViewActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new ACTextViewFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.ac_textview_title;
    }
}
