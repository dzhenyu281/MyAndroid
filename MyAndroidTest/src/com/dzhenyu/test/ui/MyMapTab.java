package com.dzhenyu.test.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dzhenyu.test.R;


/**
 * Created by onlymem on 2015/8/18.
 */
public class MyMapTab extends HorizontalScrollView {

    // tab count
    private int tabCount;
    //默认tabcount
    private static final int DEFAULT_TABCOUNT = 4;
    //字体大小
    private int tabTextSize = 14;
    //Tab padding
    private int textViewPadding = 5;
    //Tab 宽度
    private int textViewWidth;
    //下划线 显示
    private boolean showUnderLine = true;
    //下划线 颜色
    private int underLineColor;
    //下划线 高度
    private int underLineHight = 1;
    //分割线 显示
    private boolean showDivider;
    //分割线 padding
    private int dividerPadding = 12;
    //分割线 颜色
    private int dividerColor;
    //分割线、下划线 画笔
    private Paint allPaint;
    //当前的PagerView
    private int currentPagerId;
    private int lastPagerId;
    //viewPager;
    private ViewPager mViewPager;
    //布局bayoutparams
    private LinearLayout.LayoutParams defaultParams;
    //主布局 LinearLayout
    private LinearLayout mLinearTab;
    //屏幕宽度
    private int windowWidth;
    //选中tab下划线高度
    private int selectedDividerHight = 3;
    //    选中tab下划线颜色
    private int selectedDividerColor;
    //    选中tab下划线宽度
    private float curScrollOffSet;

    private MyMapTabPagerChangerListener pagerChangerListener;

    public void setPagerChangerListener(MyMapTabPagerChangerListener listener) {
        pagerChangerListener = listener;
    }

    public MyMapTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyMapTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttribute();
    }

    public MyMapTab(Context context) {
        super(context);
    }

    protected void parseAttribute() {

        setFillViewport(true);
        setWillNotDraw(false);
        initColor();
        setHorizontalScrollBarEnabled(false);

        mLinearTab = new LinearLayout(this.getContext());
        mLinearTab.setOrientation(LinearLayout.HORIZONTAL);
        mLinearTab.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        addView(mLinearTab);

        windowWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        textViewWidth = windowWidth / DEFAULT_TABCOUNT;

        defaultParams = new LinearLayout.LayoutParams(textViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        // 默认数据dp=px
        underLineHight = dip2px(underLineHight);
        dividerPadding = dip2px(dividerPadding);
        selectedDividerHight = dip2px(selectedDividerHight);
        textViewPadding = dip2px(textViewPadding);

        //画笔创建
        allPaint = new Paint();
        allPaint.setAntiAlias(true);
        allPaint.setStyle(Paint.Style.FILL);
    }

    private void initColor() {
        underLineColor = getContext().getResources().getColor(R.color.text_content);
        dividerColor = getContext().getResources().getColor(R.color.text_content);
        selectedDividerColor = Color.RED;
    }

    private int dip2px(int dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dip + 0.5f);
    }

    public void setViewPager(ViewPager pager) {
        if (pager == null) {
            throw new IllegalStateException("viewPager is null");
        }
        this.mViewPager = pager;
        if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException("viewPager adapter is null");
        }
        mViewPager.addOnPageChangeListener(new MyPagerListener());
        this.tabCount = mViewPager.getAdapter().getCount();
        notifyDateSetChange();
    }

    private void notifyDateSetChange() {
        this.mLinearTab.removeAllViews();
        for (int i = 0; i < this.tabCount; i++) {
            addTextView(i, this.mViewPager.getAdapter().getPageTitle(i).toString());
        }
        invalidate();
        upDateViewState();
    }

    private void addTextView(final int position, String title) {
        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(textViewPadding, 0, textViewPadding, 0);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });
        mLinearTab.addView(textView, position, defaultParams);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || tabCount == 0) {
            return;
        }
        int tabHight = this.getHeight();
        showUnderLine = true;
        if (showUnderLine) {
            allPaint.setColor(underLineColor);
            canvas.drawRect(0, (tabHight - underLineHight), mLinearTab.getWidth(), tabHight, allPaint);
        }
        showDivider = true;
        if (showDivider) {
            allPaint.setColor(dividerColor);
            for (int i = 1; i < tabCount; i++) {
//               View tab=mLinearTab.getChildAt(i);
//               canvas.drawLine(tab.getRight(), 12, tab.getRight(), tabHight - 12,linepoint);
                canvas.drawLine(textViewWidth * i, dividerPadding, textViewWidth * i, tabHight - dividerPadding, allPaint);
            }
        }

        View curTab = mLinearTab.getChildAt(currentPagerId);
        allPaint.setColor(selectedDividerColor);
        int lineLeft = curTab.getLeft();
        int lineRight = curTab.getRight();
        if (curScrollOffSet > 0f) {
            lineLeft += curScrollOffSet * curTab.getWidth();
            lineRight += curScrollOffSet * curTab.getWidth();
        }
        canvas.drawRect(lineLeft, tabHight - selectedDividerHight, lineRight, tabHight, allPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class MyPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPagerId = position;
            curScrollOffSet = positionOffset;
            scrollToChild(position, positionOffset);
            invalidate();
            if (pagerChangerListener != null) {
                pagerChangerListener.onTabPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            upDateViewState();
            if (pagerChangerListener != null) {
                pagerChangerListener.onTabPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (pagerChangerListener != null) {
                pagerChangerListener.onTabPageScrollStateChanged(state);
            }
        }
    }

    private void scrollToChild(int position, float positionOffset) {
        View tab = mLinearTab.getChildAt(position);
        int scrollWidth = (int) (positionOffset * tab.getWidth());
        if ((tab.getRight() + scrollWidth) > windowWidth) {
            scrollTo(tab.getRight() + scrollWidth - windowWidth, 0);
        }
    }

    private void upDateViewState() {
        for (int i = 0; i < tabCount; i++) {
            View view = mLinearTab.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabTextSize);
                if (i == mViewPager.getCurrentItem()) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.text_content));
                }
            }
        }
    }

    public interface MyMapTabPagerChangerListener {
        void onTabPageSelected(int position);

        void onTabPageScrollStateChanged(int state);

        void onTabPageScrolled(int position, float positionOffset, int positionOffsetPixels);
    }

    public interface ScrollViewPagerListener {

        /**
         * 滑动页面
         */
        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }
}
