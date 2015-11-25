package com.drawermenuframework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.drawermenuframework.R;

/**
 * Created by onlymem on 2015/10/24.
 */
public class DefaultHander extends FrameLayout {

    public static final int USER_CUSTOM_ACTION_BAR_WITH_TITLE = 0;
    public static final int USER_CUSTOM_ACTION_BAR_WITHOUT_RIGHT = 1;
    public static final int USER_CUSTOM_ACTION_BAR_WITHOUT_BACK = 2;

    private TextView titleView, rightView;
    private ImageView leftView;

    public static DefaultHander builder(Context context) {
        return new DefaultHander(context);
    }

    public DefaultHander(Context context) {
        this(context, null, 0);
    }

    public DefaultHander(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_hander_layout, this);
        titleView = (TextView) view.findViewById(R.id.custom_hander_title);
        rightView = (TextView) view.findViewById(R.id.custom_hander_right_textView);
        leftView = (ImageView) view.findViewById(R.id.custom_hander_left_image);
    }

    /**
     * @param type USER_CUSTOM_ACTION_BAR_WITH_TITLE 自定义actionbar 只包含title
     *             或 USER_CUSTOM_ACTION_BAR_WITHOUT_RIGHT  自定义actionbar 不显示right TextView
     *             或 USER_CUSTOM_ACTION_BAR_WITHOUT_BACK   自定义actionbar  不显示left ImageView
     * @return
     */
    public DefaultHander setActionBarType(int type) {
        switch (type) {
            case USER_CUSTOM_ACTION_BAR_WITH_TITLE:
                break;
            case USER_CUSTOM_ACTION_BAR_WITHOUT_RIGHT:
                rightView.setVisibility(GONE);
                break;
            case USER_CUSTOM_ACTION_BAR_WITHOUT_BACK:
                leftView.setVisibility(GONE);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 设置 title
     *
     * @param title
     * @return
     */
    public DefaultHander setTitle(String title) {
        titleView.setText(title);
        return this;
    }

    public DefaultHander setTitle(int titleId) {
        titleView.setText(getContext().getText(titleId));
        return this;
    }

    /**
     * 设置 right TextView text
     *
     * @param rightText
     * @return
     */
    public DefaultHander setRightViewText(String rightText) {
        rightView.setText(rightText);
        return this;
    }

    public DefaultHander setRightViewText(int rightTextId) {
        rightView.setText(getContext().getText(rightTextId));
        return this;
    }

    public DefaultHander setRightClickListener(OnClickListener listener) {
        rightView.setOnClickListener(listener);
        return this;
    }

    /**
     * @return left View id
     */
    public int leftViewId() {
        return leftView.getId();
    }

    /**
     * @return right view id
     */
    public int rightViewId() {
        return rightView.getId();
    }
}
