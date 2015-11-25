package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.CommonWebFragment;

/**
 * Created by onlymem on 2015/10/20.
 */
public class CommonWebActivity extends BaseFragmentActivty {

    public static String EXTRA_URL = "extra_url";
    public static String EXTRA_URL_TITLE = "extra_url_title";

    @Override
    protected BaseFragment createFragment() {
        return new CommonWebFragment();
    }

    @Override
    protected int getTitleResouceId() {
        int id = getIntent().getIntExtra(EXTRA_URL_TITLE, 0);
        if (id != 0)
            return id;
        else
            return R.string.App_name;
    }
}
