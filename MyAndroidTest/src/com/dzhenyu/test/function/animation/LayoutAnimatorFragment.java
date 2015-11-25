package com.dzhenyu.test.function.animation;

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;

/**
 * Created by onlymem on 2015/11/20.
 */
public class LayoutAnimatorFragment extends BaseFragment implements OnClickListener {

    private RelativeLayout relativeLayout;
    private LayoutTransition layoutTransition = new LayoutTransition();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_animator, container, false);
        view.findViewById(R.id.btn_control).setOnClickListener(this);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_animation_center);

        GridLayout gridLayout = (GridLayout) view.findViewById(R.id.gl_grid_layout);

        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.layout_animation);
        LayoutAnimationController controller=new LayoutAnimationController(animation);
        gridLayout.setLayoutAnimation(controller);


        /*
         // LayoutTransition 动画
        ObjectAnimator alph = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.0f);
        ObjectAnimator scalex = ObjectAnimator.ofFloat(null, "scaleX", 1.0f, 0.0f);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(null, "scaleY", 1.0f, 0.0f);
        AnimatorSet set = new AnimatorSet();
        set.play(scalex).with(scaley);
        set.setDuration(1000);
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, set);
        layoutTransition.setStagger(LayoutTransition.DISAPPEARING, 30);
        ObjectAnimator alphshow = ObjectAnimator.ofFloat(null, "alpha", 0.1f, 1.0f);
        ObjectAnimator scalexshow = ObjectAnimator.ofFloat(null, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleyshow = ObjectAnimator.ofFloat(null, "scaleY", 0.0f, 1.0f);
        ObjectAnimator transcale=ObjectAnimator.ofFloat(null, "translationY", 0.1f, 1.0f);
        AnimatorSet setshow = new AnimatorSet();
        setshow.setDuration(1000);
        setshow.play(scalexshow).with(scaleyshow);

        layoutTransition.setAnimator(LayoutTransition.APPEARING, setshow);
        layoutTransition.setStagger(LayoutTransition.APPEARING, 30);

        gridLayout.setLayoutTransition(layoutTransition);
        */

        return view;
    }

    @Override
    public void onClick(View v) {
        if (relativeLayout.getVisibility() == View.VISIBLE) {
            relativeLayout.setVisibility(View.GONE);
        } else if (relativeLayout.getVisibility() == View.GONE) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}
