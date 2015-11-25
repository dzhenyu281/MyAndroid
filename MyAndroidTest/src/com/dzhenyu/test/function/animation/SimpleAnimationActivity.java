package com.dzhenyu.test.function.animation;

import com.dzhenyu.test.BaseFragmentActivty;
import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.function.animation.SimpleAnimationFragment;

/**
 * Created by onlymem on 2015/11/12.
 */
public class SimpleAnimationActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new SimpleAnimationFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.animation_simple;
    }
}
