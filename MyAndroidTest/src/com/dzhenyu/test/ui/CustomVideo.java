package com.dzhenyu.test.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.dzhenyu.test.R;


/**
 * Created by onlymem on 2015/8/28.
 */
public class CustomVideo extends VideoView {

    public int mVideoWidth;
    public int mVideoHeight;
    public static float DEFAULT_VIDEO_WIDTH = 720.0f;
    public static float DEFAULT_VIDEO_HEIGHT = 404.0f;
    private int scrollLimit = 9;

    private Toast mToast;
    /**
     * 音频管理器
     */
    private AudioManager audioManager;
    private int maxVolume;

    private Context context;

    //    private Window mwindow;
    public CustomVideo(Context context) {
        super(context);
    }

    public CustomVideo(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        scrollLimit = (int) (this.context.getResources().getDisplayMetrics().density * scrollLimit);
        audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getHolder().setFixedSize(mVideoWidth, mVideoHeight);
        setMeasuredDimension(mVideoWidth, mVideoHeight);
    }

    public void setOrientation(int OrientationType) {
        if (OrientationType == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE) {
            // 横屏
            mVideoWidth = getWindowWidth();
            mVideoHeight = getWindowHeight();
        } else {
            mVideoWidth = getWindowWidth();
            mVideoHeight = (int) (mVideoWidth * DEFAULT_VIDEO_HEIGHT / DEFAULT_VIDEO_WIDTH);
        }
    }

    public int getWindowWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getWindowHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    private float xLastMotion;
    private float yLastMotion;

    private float xMoveLength;
    private float yMoveLength;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xLastMotion = x;
                yLastMotion = y;
                xMoveLength = 0;
                yMoveLength = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float delatX = x - xLastMotion;
                float delatY = y - yLastMotion;
                xMoveLength += Math.abs(delatX);
                yMoveLength += Math.abs(delatY);
                if (Math.abs(delatX) < scrollLimit && Math.abs(delatY) < scrollLimit) {
                    return true;
                } else if (Math.abs(delatX) < Math.abs(delatY)) {
                    if (x < getWidth() / 2) {
                        onTouchChangeBrightness(delatY, mVideoHeight);
                    } else {
                        onTouchChangeVolume(delatY, mVideoHeight);
                    }
                } else if (Math.abs(delatX) > Math.abs(delatY)) {
                    onTouchChangeProgress(delatX, mVideoWidth);
                }
                xLastMotion = x;
                yLastMotion = y;
                break;
            case MotionEvent.ACTION_UP:
                boolean isClick = false;
                if (xMoveLength < scrollLimit && yMoveLength < scrollLimit) {
                    isClick = true;
                }
                if (isClick) {
                    if (isPlaying()) {
                        stop();
                    } else {
                        start();
                    }
                }
                break;
        }
        return true;
    }

    public void stop() {
        this.pause();
    }

    /**
     * 声音调节
     */
    protected void onTouchChangeVolume(float delatY, float screenHeight) {
        int value = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + (int) ((-delatY / screenHeight) * maxVolume * 3);
        value = Math.max(Math.min(value, maxVolume), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value, 0);
        showToast(value * 100 / maxVolume, SCREEN_TYPE_VOLUME);
    }

    /**
     * 播放进度调节
     */
    protected void onTouchChangeProgress(float delatX, float screenWidth) {
        if ((delatX > 0 && canSeekForward()) || (delatX < 0 && canSeekBackward())) {
            int value = (int) ((delatX / screenWidth) * getDuration());
            value = Math.min(value, 30000) + getCurrentPosition();
            int progress = Math.max(Math.min(value, getDuration()), 0);
            this.seekTo(progress);
            showToast(progress, SCREEN_TYPE_PROGRESS);
        }
    }

    /**
     * 亮度调节
     */
    private void onTouchChangeBrightness(float delatY, float screenHeight) {
        ContentResolver contentResolver = this.context.getContentResolver();
        boolean autoMatic;
        try {
            autoMatic = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
            if (autoMatic) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
            int value = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            value = value + (int) ((-delatY / screenHeight) * 255);
            value = Math.min(Math.max(value, 0), 255);
            Log.i("---->", "---onTouchChangeBrightness value->" + value);
//            WindowManager.LayoutParams params= window.getAttributes();
//            params.screenBrightness=value;
//            window.setAttributes(params);
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, value);
            showToast(value * 100 / 255, SCREEN_TYPE_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Button toastButton;
    public final static int SCREEN_TYPE_VOLUME = 1;
    public final static int SCREEN_TYPE_BRIGHTNESS = 2;
    public final static int SCREEN_TYPE_PROGRESS = 3;

    private void showToast(int progress, int type) {
        if (mToast == null) {
            mToast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            toastButton = (Button) view.findViewById(R.id.btn_toast);
            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
            toastButton.setGravity(Gravity.CENTER);
        }
        toastButton.setText(progress + "%");
        switch (type) {
            case SCREEN_TYPE_VOLUME:
                toastButton.setBackgroundResource(R.drawable.volume);
                break;
            case SCREEN_TYPE_BRIGHTNESS:
                toastButton.setBackgroundResource(R.drawable.brightness);
                break;
            case SCREEN_TYPE_PROGRESS:
                toastButton.setText("播放进度：" + progress / 1000);
                break;
        }
        mToast.show();
    }
}
