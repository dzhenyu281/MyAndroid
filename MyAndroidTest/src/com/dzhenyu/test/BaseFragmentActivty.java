package com.dzhenyu.test;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

import com.dzhenyu.test.fragment.BaseFragment;

/**
 * Created by onlymem on 2015/9/8.
 */
public abstract class BaseFragmentActivty extends BaseActivity {

    private FragmentManager fm;
    private BaseFragment baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(-1);
        fm = getSupportFragmentManager();
        if (baseFragment == null) {
            baseFragment = createFragment();
            fm.beginTransaction().replace(R.id.fl_framelayout, baseFragment).commit();
        }
        setTitleContent();
    }

    private void setTitleContent() {
        ((TextView) findViewById(R.id.tv_title)).setText(getTitleResouceId());
    }

    protected abstract BaseFragment createFragment();

    protected abstract int getTitleResouceId();


    protected void onClickRightButtonAction() {
        baseFragment.onClickRightAction();
    }

    protected void onClickBack() {
        if (baseFragment.onClickBackBtn())
            finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return baseFragment.onTouchEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return baseFragment.onKeyDown(keyCode, event);
    }

}
