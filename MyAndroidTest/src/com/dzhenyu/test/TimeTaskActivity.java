package com.dzhenyu.test;

/**
 * Created by onlymem on 2015/9/17.
 */
public class TimeTaskActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new TimeTaskFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.time_task_title;
    }
}
