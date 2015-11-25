package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.VideoPlayFragment;

/**
 * Created by onlymem on 2015/9/8.
 */
public class VideoPlayActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new VideoPlayFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.video_play_title;
    }
}
