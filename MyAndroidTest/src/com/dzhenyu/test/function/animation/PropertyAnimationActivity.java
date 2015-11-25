package com.dzhenyu.test.function.animation;

import com.dzhenyu.test.BaseFragmentActivty;
import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.function.animation.PropertyAnimationFragment;

/**
 * Created by onlymem on 2015/11/16.
 */
public class PropertyAnimationActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new PropertyAnimationFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.property_animation;
    }
}
