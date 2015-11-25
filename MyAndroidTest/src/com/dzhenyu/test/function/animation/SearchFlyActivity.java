package com.dzhenyu.test.function.animation;

import com.dzhenyu.test.BaseFragmentActivty;
import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.function.animation.SearchFlyFragment;

/**
 * Created by onlymem on 2015/11/18.
 */
public class SearchFlyActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new SearchFlyFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.text_in_out;
    }
}
