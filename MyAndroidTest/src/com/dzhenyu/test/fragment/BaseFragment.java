package com.dzhenyu.test.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dzhenyu.test.BaseFragmentActivty;

/**
 * Created by onlymem on 2015/9/9.
 */
public class BaseFragment extends Fragment {

    private BaseFragmentActivty baseFragmentActivty;

    public Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            baseFragmentActivty = (BaseFragmentActivty) activity;
            mContext = baseFragmentActivty;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * true finis Activity
     */
    public boolean onClickBackBtn() {
        return true;
    }

    public void setRightBtnEnabled(boolean enabled) {
        baseFragmentActivty.setRightBtnEnabled(enabled);
    }

    private boolean getRightBtnEnable() {
        return baseFragmentActivty.getRightBtnEnabled();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void onClickRightAction() {
        return;
    }

    public void setHeaderVisibility(int visibility) {
        baseFragmentActivty.setHeaderVisibility(visibility);
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        return false;
    }
}
