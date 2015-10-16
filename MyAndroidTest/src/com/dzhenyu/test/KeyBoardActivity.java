package com.dzhenyu.test;

/**
 * Created by onlymem on 2015/9/11.
 */
public class KeyBoardActivity extends BaseFragmentActivty {
    @Override
    protected BaseFragment createFragment() {
        return new KeyBoardFragment();
    }

    @Override
    protected int getTitleResouceId() {
        return R.string.key_board_title;
    }
}
