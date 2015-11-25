package com.dzhenyu.test;

import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.fragment.MusicPlayFragment;

/**
 * Created by onlymem on 2015/9/11.
 */
public class MusicPlayActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new MusicPlayFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.music_play_title;
    }
}
