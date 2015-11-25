package com.drawermenuframework;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.drawermenuframework.view.DefaultHander;


/**
 * Created by onlymem on 2015/10/23.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    /**
     * hander view type
     */
    private int handerViewType = -1;
    /**
     * Toolbar 作为Actionbar
     */
    public static final int USER_TOOLBAR = 0;
    /**
     * 自定义 Actionbar
     */
    public static final int USER_CUSTOM_ACTION_BAR = 1;
    /**
     * DefaultHandler 作为actionbar
     */
    public static final int USER_DEFAULT_CUSTOM_ACTION_BAR = 2;

    private FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private BaseFragment baseFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.base_activity_layout);
        initViewHander();
        baseFragment = setBaseFragment();
        if (baseFragment != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction().add(R.id.base_content, baseFragment).commit();
        }
    }

    /**
     * 选择 actionbar 样式
     *
     * @return USER_TOOLBAR  使用toolbar作为hander
     * 或 USER_CUSTOM_ACTION_BAR   自定义actionbar,需重写customHanderView方法
     * 或 USER_DEFAULT_CUSTOM_ACTION_BAR
     */
    private void setActionBarStyle(int type) {
        this.handerViewType = type;
    }

    private void initViewHander() {
        View hander = null;
        FrameLayout handerview = (FrameLayout) findViewById(R.id.base_hander);
        switch (handerViewType) {
            case USER_TOOLBAR:
                handerview.setVisibility(View.GONE);
                hander = LayoutInflater.from(this).inflate(R.layout.toolbar_layout, null);
                Toolbar toolbar = (Toolbar) hander.findViewById(R.id.toolbar_hander);
                setToolbarAttr(toolbar);
                setSupportActionBar(toolbar);
                break;
            case USER_CUSTOM_ACTION_BAR:
                hander = customHanderView();
                if (hander != null) {
                    handerview.addView(hander, params);
                }
                break;
            case USER_DEFAULT_CUSTOM_ACTION_BAR:
                hander = DefaultHander.builder(this);
                if (hander != null) {
                    setDefaultCustomActionBarAttr((DefaultHander) hander);
                    handerview.addView(hander, params);
                }
                break;
            default:
                handerview.setVisibility(View.GONE);
                break;
        }
    }


    protected void setToolbarAttr(Toolbar toolbar) {
    }

    protected void setDefaultCustomActionBarAttr(DefaultHander defaultHander) {
    }

    protected View customHanderView() {
        return null;
    }

    public abstract BaseFragment setBaseFragment();


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (baseFragment != null)
            baseFragment.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
