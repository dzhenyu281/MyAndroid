package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.ImitationAnimationFragment;

/**
 * Created by onlymem on 2015/11/9.
 */
public class ImitationAnimationActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new ImitationAnimationFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.animation_imitation;
    }
}
