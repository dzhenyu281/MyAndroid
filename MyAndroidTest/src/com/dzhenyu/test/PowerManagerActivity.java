package com.dzhenyu.test;

/**
 * Created by onlymem on 2015/9/18.
 */
public class PowerManagerActivity  extends  BaseFragmentActivty{
    @Override
    protected BaseFragment createFragment() {
        return new PowerManagerFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.power_manager_title;
    }
}
