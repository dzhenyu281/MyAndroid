package com.dzhenyu.test.function.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;

/**
 * Created by onlymem on 2015/11/16.
 */
public class PropertyAnimationFragment extends BaseFragment implements View.OnClickListener {

    static String TAG = PropertyAnimationFragment.class.getSimpleName();
    Button animationBtn1, animationBtn2;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.property_animation_layout, container, false);
        animationBtn1 = (Button) view.findViewById(R.id.property_animation_1);
        animationBtn1.setOnClickListener(this);
        animationBtn2 = (Button) view.findViewById(R.id.property_animation_2);
        animationBtn2.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.property_image);
        view.findViewById(R.id.property_animation_3).setOnClickListener(this);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        initObjectAnimation();
        initIntAnimation();
        switch (view.getId()) {
            case R.id.property_animation_1:
                objectAnimator.start();
                break;
            case R.id.property_animation_2:
                valueAnimator.start();
                break;
            case R.id.property_animation_3:
                AnimatorSet set=new AnimatorSet();
                set.play(objectAnimator).with(valueAnimator);
                set.start();

//                TimeAnimator timeAnimator = new TimeAnimator();
//                timeAnimator.setCurrentPlayTime(System.currentTimeMillis());
//                timeAnimator.setDuration(1000);
//                timeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
//                    @Override
//                    public void onTimeUpdate(TimeAnimator animation, long totalTime,
//                                             long deltaTime) {
//                        Log.i(TAG, "---time Animator->: totalTime:" + totalTime + ",  deltaTime:" +
//                                   deltaTime);
//                    }
//                });
//                System.currentTimeMillis();
//                timeAnimator.start();
                break;
        }
    }

    private ValueAnimator valueAnimator, objectAnimator;

    private void initIntAnimation() {
        int width = animationBtn1.getWidth();
        valueAnimator =
                ValueAnimator.ofInt(animationBtn2.getWidth(), width);
        valueAnimator.setDuration(1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.i(TAG, "------->" + value);
                animationBtn2.setWidth(value);
            }
        });
    }

    private void initObjectAnimation() {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        int screenHight = mContext.getResources().getDisplayMetrics().heightPixels;

        Point startPoint = new Point(screenWidth, screenHight / 10);
        Point endPoint = new Point(0, (screenHight / 10) * 3);

        objectAnimator =
                ValueAnimator.ofObject(new MyTypeEvaluetor(), startPoint, endPoint);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point value = (Point) animation.getAnimatedValue();
                imageView.setX(value.x);
                imageView.setY(value.y);
            }
        });
        final Animation anim1 = new AlphaAnimation(1.0f, 0.0f);
        anim1.setDuration(1000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(anim1);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(1000);
    }


    class Point {
        public float x;
        public float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private class MyTypeEvaluetor implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction,
                              Point startValue,
                              Point endValue) {
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = (float) (Math.pow(x, 2) * (1 / 1560.0) - ((103 / 130.0) * x) + 360);
            Point point = new Point(x, y);
            return point;
        }
    }


    /**
     * TODO View 宽度和高度获取
     * view中onCreateView中还没有被绘制,view是被隐藏的，所以不能通过view.getWidth获取view的宽度
     * 如非必须，可以不再onCreateView，onActivityCreate中获取宽度
     */
    private void getViewWidthHight(final View view, final View parentView) {

        /**
         *  TODO 方法一：测量法 ,这种测量方法与xml中配置的width有关系,故而这个方法不是很准确
         *  MeasureSpec.AT_MOST 最大尺寸，当view的lauyout_width或者lauyout_hight设置为wrap_content时，view的
         *       高宽会随着内容变化，此时控件尺寸只要不超过父控件的最大尺寸即可。
         *  MeasureSpec.EXACTLY 是精准尺寸，当view的lauyout_width或者lauyout_hight设置为fill_parent（match_parent）或则固定的尺寸时，
         *        都是精准尺寸；
         *  MeasureSpec.UNSPECIFIED 未指定尺寸，一般是由父控件指定最小的尺寸
         */
        int model = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        view.measure(model, model);
        Log.i(TAG, "--measure ->" + view.getMeasuredWidth() + ",,," + view.getMeasuredHeight());
        Log.i(TAG, "--->" + view.getY() + ",,," + view.getX());

        //TODO 方法二：增加view绘制之前监控，在View绘制之前获取
        ViewTreeObserver vt = view.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.i(TAG, "--ViewTreeObserver->" + view.getWidth() + ",,," + view.getHeight());
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                Log.i(TAG, "--->" + view.getY() + ",,," + view.getX());
                return false;
            }
        });

        //TODO 方法三：增加view布局的监听
        ViewTreeObserver vt1 = parentView.getViewTreeObserver();
        vt1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                parentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.i(TAG, "--onGlobalLayout->" + view.getWidth() + ",,," + view.getHeight());
                Log.i(TAG, "--->" + view.getY() + ",,," + view.getX());
            }
        });
    }
}
