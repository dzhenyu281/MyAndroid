package com.dzhenyu.test;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * drawable test show
 */
public class DrawableShowActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private String pagerFlag = "";

    public static final String DRAWABLE_SHOW_EXTRA = "drawable_show_extra";

    private SeekBar mSeekBar;

    private View drawableDisplay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerFlag = getIntent().getStringExtra(DRAWABLE_SHOW_EXTRA);

        setContentView(R.layout.drawable_show_layout);
        mSeekBar = (SeekBar) findViewById(R.id.drawable_seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setProgress(0);
        drawableDisplay = findViewById(R.id.drawable_display_view);
        this.setHeaderVisibility(View.GONE);
        drawableShow();
    }

    private void drawableShow() {
        int resourceId = -1;
        switch (pagerFlag) {
            case "GradientDrawable":
                resourceId = R.drawable.drawable_gradient;
                break;
            case "BitmapDrawable":
                resourceId = R.drawable.drawable_bitmap;
                break;
            case "NinePatchDrawable":
                break;
            case "InsetDrawable":
                resourceId = R.drawable.drawable_inset;
                break;
            case "ClipDrawable":
                resourceId = R.drawable.drawable_clip;
                break;
            case "ScaleDrawable":
                resourceId = R.drawable.drawable_scale;
                break;
            case "RotateDrawable":
                resourceId = R.drawable.drawable_rotate;
                break;
            case "AnimationListDrawable":
                resourceId = R.drawable.drawable_animation;
                break;
            case "LayerDrawable":
                break;
            case "TransitionDrawable":
                break;
            case "LevelListDrawable":
                resourceId=R.drawable.drawable_level;
                break;
            case "StateListDrawable":
                break;
            default:
                break;
        }
        if (resourceId != -1)
            drawableDisplay.setBackgroundResource(resourceId);
        if (resourceId == R.drawable.drawable_animation) {
            ((AnimationDrawable)drawableDisplay.getBackground()).start();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Drawable drawable = drawableDisplay.getBackground();
        if (drawable != null)
            drawable.setLevel(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
