package com.dzhenyu.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 1、标题栏 返回按钮
 * 2、标题栏 右上角菜单
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout btnBack;
    private TextView rightText;
    private RelativeLayout rlRightBtn;
    private RelativeLayout rlHeaderLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_fragment_layout);
        if (layoutResID > -1) {
            LayoutInflater.from(this).inflate(layoutResID, (ViewGroup) findViewById(R.id.fl_framelayout));
        }
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        /**返回按钮*/
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        /**右边的标签*/
        rightText = (TextView) findViewById(R.id.tv_title_bar_right);
        rlRightBtn = (RelativeLayout) findViewById(R.id.rl_title_bar_right);
        rlRightBtn.setOnClickListener(this);
    }

    protected void setActivityTitle(int resourceId) {
        ((TextView) findViewById(R.id.tv_title)).setText(resourceId);
    }

    protected void setActivityTitle(String titleText) {
        ((TextView) findViewById(R.id.tv_title)).setText(titleText);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                onClickBack();
                break;
            case R.id.rl_title_bar_right:
                onClickRightButtonAction();
                break;
        }
    }

    protected void onClickRightButtonAction() {
    }

    protected void onClickBack() {
        finish();
    }

    /**
     * 获取 右上角按钮文本内容
     */
    protected void setRightText(int resouceId) {
        rightText.setText(resouceId);
    }

    /**
     * 获取 右上角按钮文本内容,颜色
     */
    protected void setRightTextAttrbute(int resouceId, int color) {
        setRightText(resouceId);
        setRightTextColor(color);
    }

    /**
     * 获取 右上角按钮颜色
     */
    protected void setRightTextColor(int color) {
        //测试 color.parse()
        rightText.setTextColor(getResources().getColor(color));
    }

    /**
     * 获取 返回按钮 显示
     */
    protected void setBtnBackVisibility(int visibility) {
        btnBack.setVisibility(visibility);
    }

    /**
     * 设置 右上角按钮 灰显
     */
    protected void setRightBtnEnabled(boolean enabled) {
        rlRightBtn.setEnabled(enabled);
    }

    /**
     * 获取 右上角按钮 是否灰显
     */
    protected boolean getRightBtnEnabled() {
        return rlRightBtn.isEnabled();
    }

    /**
     * 设置是否显示 头布局
     */
    protected void setHeaderVisibility(int visibility) {
        findViewById(R.id.rl_header_layout).setVisibility(visibility);
    }

}
