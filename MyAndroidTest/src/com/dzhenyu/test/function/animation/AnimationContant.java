package com.dzhenyu.test.function.animation;

import com.dzhenyu.test.R;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by onlymem on 2015/11/12.
 */
public class AnimationContant {

    public static final Map<String, Integer> SIMPLE_ANIMATION = new LinkedHashMap<>();

    static {
        SIMPLE_ANIMATION.put("alpha1", R.anim.a1);
        SIMPLE_ANIMATION.put("alpha2", R.anim.a2);
        SIMPLE_ANIMATION.put("alpha_rotate", R.anim.alpha_rotate);
        SIMPLE_ANIMATION.put("alpha_scale", R.anim.alpha_scale);
        SIMPLE_ANIMATION.put("alpha_scale_rotate", R.anim.alpha_scale_rotate);
        SIMPLE_ANIMATION.put("alpha_translate", R.anim.alpha_translate);
        SIMPLE_ANIMATION.put("drawroll_in", R.anim.drawroll_in);
        SIMPLE_ANIMATION.put("drawroll_out", R.anim.drawroll_out);
        SIMPLE_ANIMATION.put("gallery_in", R.anim.gallery_in);
        SIMPLE_ANIMATION.put("zoom_exit", R.anim.zoom_exit);
        SIMPLE_ANIMATION.put("zoom_enter", R.anim.zoom_enter);
        SIMPLE_ANIMATION.put("in_translate_top", R.anim.in_translate_top);


    }
}
