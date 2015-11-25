package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.ImageScaleTypeFragment;

/**
 * Created by onlymem on 2015/10/19.
 */
public class ImageScaleTypeActivity extends BaseFragmentActivty {

    @Override
    protected BaseFragment createFragment() {
        return new ImageScaleTypeFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.image_view_scaletype_title;
    }
}
