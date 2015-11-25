package com.dzhenyu.test.function.eventbus;

import com.dzhenyu.test.BaseFragmentActivty;
import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;

/**
 * Created by onlymem on 2015/11/21.
 */
public class EventBusSecondActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new EventBusSecondFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.eventbus_second_pager;
    }
}
