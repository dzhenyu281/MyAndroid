package com.dzhenyu.test.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by onlymem on 2015/11/18.
 */
public class SearchFlyView extends FrameLayout {

    // 显示的文字
    private String[] showSigns;

    private int minTextSize = 15;
    private int maxTextSize = 25;

    // 一次最多显示
    private int maxShowOneTime = 10;


    // 文字 飞入
    public static final int TYPE_SHOW_IN = 0;
    // 文字 飞出
    public static final int TYPE_SHOW_OUT = 1;
    /**
     * 飞入飞出类型 ，默认飞入
     */
    private int showType = TYPE_SHOW_IN;
    // 动画时间
    private long animationDuration = 1000;


    public SearchFlyView(Context context) {
        super(context);
    }

    public SearchFlyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchFlyView(Context context, AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("---->", "---->" + getParent());
    }

    public void setShowSigns(String strs[]) {
        if (strs == null || strs.length <= 0) {
            throw new IllegalStateException("strs is null or length is 0");
        }
        this.showSigns = strs;
    }

    //-------------------start----ValueAnimation 完成动画------------------------------------------
    private static final int TYPE_OLD_SHOW_IN = 1;
    private static final int TYPE_NEW_SHOW_IN = 2;
    private static final int TYPE_OLD_SHOW_OUT = 3;
    private static final int TYPE_NEW_SHOW_OUT = 4;

    private AnimatorSet getAnimatorSet(final View view, Point startPoint, Point endPoint,
                                       int type) {
        AnimatorSet animatiorSet = new AnimatorSet();
        animatiorSet.setDuration(animationDuration);
        ValueAnimator translateAnim =
                ValueAnimator.ofObject(new TranslateTypeEvaluetor(), startPoint,
                                       endPoint);
        translateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                view.setX(point.x);
                view.setY(point.y);
            }
        });
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator scaleAnimX = null;
        ObjectAnimator scaleAnimY = null;
        switch (type) {
            case TYPE_OLD_SHOW_IN:
                scaleAnimX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
                scaleAnimY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
                animatiorSet.play(translateAnim).with(alphaAnim).with(scaleAnimX).with(scaleAnimY);
                break;
            case TYPE_NEW_SHOW_IN:
                scaleAnimX = ObjectAnimator.ofFloat(view, "scaleX", 2f, 1.0f);
                scaleAnimY = ObjectAnimator.ofFloat(view, "scaleY", 2f, 1.0f);
                animatiorSet.play(translateAnim).with(scaleAnimX).with(scaleAnimY);
                break;
            case TYPE_OLD_SHOW_OUT:
                scaleAnimX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 2.0f);
                scaleAnimY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 2.0f);
                animatiorSet.play(translateAnim).with(scaleAnimX).with(scaleAnimY).with(alphaAnim);
                break;
            case TYPE_NEW_SHOW_OUT:
                scaleAnimX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
                scaleAnimY = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
                alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
                animatiorSet.play(translateAnim).with(scaleAnimX).with(scaleAnimY).with(alphaAnim);
                break;
        }
        return animatiorSet;
    }

    class TranslateTypeEvaluetor implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue,
                              Point endValue) {
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = startValue.y + fraction * (endValue.y - startValue.y);
            return new Point(x, y);
        }
    }

    class Point {
        public float x;
        public float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                // 同一坐标点
                if (p.x == this.x && p.y == this.y) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
    //-------------------end---ValueAnimation 完成动画---------------------------------------------


    // 计算 当前要显示的 值
    private String[] getNextShowSigns() {
        if (showSigns.length <= maxShowOneTime) {
            return showSigns;
        } else {
            Random random = new Random();
            String[] nextShow = new String[maxShowOneTime];
            for (int i = 0; i < maxShowOneTime; i++) {
                nextShow[i] = showSigns[random.nextInt(showSigns.length)];
            }
            return nextShow;
        }
    }

    public void setShowType(int type) {
        this.showType = type;
    }

    private float centerwidth = 0;
    private float centerhight = 0;

    //显示 signs
    public void show() {
        if (showSigns == null) {
            throw new IllegalStateException("show signs is null,please do setShowSings");
        }
        centerwidth = getWidth() / 2;
        centerhight = getHeight() / 2;

        hideAllTextView(showType == TYPE_SHOW_IN ? TYPE_OLD_SHOW_IN : TYPE_OLD_SHOW_OUT);

        //加载新的textview
        addTextView(showType == TYPE_SHOW_IN ? TYPE_NEW_SHOW_IN : TYPE_NEW_SHOW_OUT,
                    getNextShowSigns());
    }

    /**
     * 显示 TextView
     *
     * @param type
     * @param showSigns
     */
    private void addTextView(int type, String[] showSigns) {

        //两个空间最小的差距
        float minWidth = centerwidth / showSigns.length;
        float minHight = centerhight / showSigns.length;

        Map<Point, Point> viewPoints = new LinkedHashMap<>();
        LinkedList<ChildTextViewAtrr> textViewAttr = new LinkedList<>();

        List<Float> xItems=new ArrayList<>();
        List<Float> yItems=new ArrayList<>();
        for(int i=0;i<showSigns.length;i++) {
            xItems.add(i * minWidth);
            yItems.add(i * minHight + minHight / 2);
        }

//        for (int i = 0; i < showSigns.length; i++) {
//            Paint paint = new Paint();
//            Rect rect = new Rect();
//            paint.getTextBounds(showSigns[i], 0, showSigns[i].length(), rect);
//            textViewAttr.add(new ChildTextViewAtrr(rect.width(), rect.height()));
//        }
//
//        // 初步生成 xy坐标
        Random random = new Random();
//        int i = 0;
//        while (i < showSigns.length) {
//            int x = random.nextInt((int) (centerwidth * 1.5)) + (int) (centerwidth * 0.25);
//            int y = random.nextInt((int) (centerhight * 1.5)) + (int) (centerhight * 0.25);
//            Point point = new Point(x, y);
//            Point rightBottom =
//                    new Point(textViewAttr.get(i).width + x, textViewAttr.get(i).hight + y);
//            // 坐标区域和 是否已存在判断
//            if (rightBottom.x >= centerwidth * 1.5 ||
//                rightBottom.y >= centerhight * 1.5 ||   // xy 区域判断
//                viewPoints.containsKey(point)) {
//                continue;
//            }
//            if (!checkRadius(point, rightBottom, minWidth, minHight) ||
//                !checkXY(viewPoints, point, rightBottom)) {
//                continue;
//            }
//            viewPoints.put(point, rightBottom);
//            i++;
//        }
//        LinkedList<Point> endPoints = new LinkedList<>();
//        for (Map.Entry<Point, Point> viewPoint : viewPoints.entrySet()) {
//            endPoints.add(viewPoint.getKey());
//        }
        for (int i = 0; i < showSigns.length; i++) {
            final TextView textView = new TextView(getContext());
            textView.setText(showSigns[i]);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            // 颜色设置
            int ranColor = 0xff000000 | random.nextInt(0x0077ffff);
            textView.setTextColor(ranColor);
            // 设置TextView 颜色
            //            textView.setShadowLayer(1, 1, 1, 0xdd696969);
            //文字大小
            int size = minTextSize + random.nextInt((maxTextSize - minTextSize));
            textView.setTextSize(size);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("---->", "---->custom Search Fly view " + textView.getText());
                }
            });

//            final Point endPoint = endPoints.get(i);
            final Point endPoint = new Point(xItems.remove(random.nextInt(xItems.size())),
                                             yItems.remove(random.nextInt(yItems.size())));
            // 最终的坐标 重新调整
            Log.i("---->", "---->custom text view endpoint " + endPoint.toString());
            AnimatorSet animatorSet = null;
            switch (type) {
                case TYPE_NEW_SHOW_IN:
                    animatorSet = getAnimatorSet(textView, getPoint(endPoint), endPoint, type);
                    break;
                case TYPE_NEW_SHOW_OUT:
                    animatorSet =
                            getAnimatorSet(textView, new Point(centerwidth, centerhight), endPoint,
                                           type);
                    break;
            }
            addView(textView);
            if (animatorSet != null) {
                animatorSet.start();
            }
        }
    }

    class ChildTextViewAtrr {
        int width;
        int hight;

        ChildTextViewAtrr(int w, int h) {
            this.width = w;
            this.hight = h;
        }
    }

    /**
     * 距中心点的距离
     *
     * @param rightBottom
     * @param minwidth
     * @param minHight
     * @return
     */
    private boolean checkRadius(Point leftTop, Point rightBottom, float minwidth, float minHight) {
        double r1 =
                Math.pow((rightBottom.x - centerwidth), 2) +
                Math.pow((rightBottom.y - centerhight), 2);
        double r2 = Math.pow((leftTop.x - centerwidth), 2) + Math.pow((leftTop.y - centerhight), 2);
        double r = (Math.pow(2 * minwidth, 2) + Math.pow(2 * minHight, 2));
        if (r > r1 || r > r2) {
            return false;
        }
        return true;
    }

    /**
     * 坐标判断 true 表示可用
     *
     * @param points
     * @param leftTop
     * @param rightBottom
     * @return
     */
    private boolean checkXY(Map<Point, Point> points, Point leftTop, Point rightBottom) {
        for (Map.Entry<Point, Point> viewPoint : points.entrySet()) {
            if ((viewPoint.getKey().x < rightBottom.x && viewPoint.getValue().x > rightBottom.x) ||
                (viewPoint.getKey().x < leftTop.x && viewPoint.getValue().x > leftTop.x) ||
                (viewPoint.getKey().y < rightBottom.y && viewPoint.getValue().y > rightBottom.y) ||
                (viewPoint.getValue().y < leftTop.y && viewPoint.getValue().y > leftTop.y)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 隐藏现有的 TextView
     *
     * @param type
     */
    private void hideAllTextView(int type) {
        Point endPoint = null;
        Point startPoint = null;
        for (int i = 0; i < getChildCount(); i++) {
            final TextView textView = (TextView) getChildAt(i);
            if (textView.getVisibility() == VISIBLE) {
                startPoint = new Point(textView.getX(), textView.getY());
                // 出去的位置没有确定
                switch (type) {
                    case TYPE_OLD_SHOW_IN:
                        endPoint = new Point(centerwidth, centerhight);
                        break;
                    case TYPE_OLD_SHOW_OUT:
                        endPoint = getPoint(startPoint);
                        break;
                }
                AnimatorSet animator = getAnimatorSet(textView, startPoint, endPoint, type);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        removeView(textView);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                animator.start();
            }
        }
    }

    private Point getPoint(Point startPoint) {
        float k = (startPoint.y - centerhight) / (startPoint.x - centerwidth);
        float b = startPoint.y - (k * startPoint.x);
        float c = centerwidth / 4;
        float x = startPoint.x > centerwidth ? startPoint.x + c : startPoint.x - c;
        return new Point(x, (k * x + b));
    }

}
