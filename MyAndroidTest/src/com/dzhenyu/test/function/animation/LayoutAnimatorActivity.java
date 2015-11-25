package com.dzhenyu.test.function.animation;

import com.dzhenyu.test.BaseFragmentActivty;
import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.function.animation.LayoutAnimatorFragment;

/**
 * Created by onlymem on 2015/11/20.
 */
public class LayoutAnimatorActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new LayoutAnimatorFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.layout_animation;
    }
}
