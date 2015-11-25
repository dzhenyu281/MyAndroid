package com.dzhenyu.test.function.eventbus;

import com.dzhenyu.test.BaseFragmentActivty;
import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;

/**
 * Created by onlymem on 2015/11/21.
 */
public class EventBusFristActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new EventBusFristFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.eventbus_frist_pager;
    }
}
