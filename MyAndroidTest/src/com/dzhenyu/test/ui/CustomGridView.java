package com.dzhenyu.test.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

/**
 * Created by onlymem on 2015/11/17.
 */
public class CustomGridView extends GridView {
    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minWidth = 0;
        int minhight = 0;
        Log.i("custom","---->"+getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == 0) {
                minWidth = child.getMeasuredWidth();
                minhight = child.getMeasuredHeight();
            }
            Log.i("custom","----->: "+child.getMeasuredWidth()+"   hight: "+child.getMeasuredHeight());
            minWidth = Math.min(minWidth, child.getMeasuredWidth());
            minhight = Math.min(minhight, child.getMeasuredHeight());
        }


        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(minWidth, minhight);
        }

        Log.i("custom","----->: ---------");
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //            child.measure(modelWidth, modelHight);
            Log.i("custom","----->: "+child.getMeasuredWidth()+"   hight: "+child.getMeasuredHeight());
        }

        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

}
