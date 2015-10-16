package com.dzhenyu.test.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzhenyu.test.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/9/12.
 */
public class MusicPlayControl extends FrameLayout implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    protected static int CHANGE_PLAY_PROGRESS = 1;
    private View view;
    //播放时长，音乐总时长
    private TextView tvPlayTime, tvMusicTime;
    // 播放方式，播放列表，下一曲，上一曲，播放
    private ImageButton btnPlayType, btnPlayList, btnPlayNext, btnPlayPrevious, btnPlayState;

    private MediaPlayer mediaPlayer;

    private SeekBar seekBar;
    private List<File> playList = new ArrayList<>();
    //当前播放 位置
    private int currentPlayPosition = -1;
    private int curPlayProgress = 0;
    // 播放方式
    private PlayType curPlayType = PlayType.ORDER_LIST;
    private Context context;

    public enum PlayType {
        //顺序播放
        ORDER_LIST,
        // 随机播放
        RANDOM,
        //列表循环
        LOOP_LIST,
        //单曲循环
        LOOP_SINGLE
    }

    public MusicPlayControl(Context context) {
        this(context, null);
    }

    public MusicPlayControl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.music_play_control, this);
        this.context = context;
        initView();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }


    /**
     * 初始化 view
     */
    private void initView() {
        if (view == null)
            return;
        tvPlayTime = (TextView) view.findViewById(R.id.play_time);
        tvMusicTime = (TextView) view.findViewById(R.id.play_music_time);

        btnPlayList = (ImageButton) view.findViewById(R.id.btn_play_lsit);
        btnPlayState = (ImageButton) view.findViewById(R.id.btn_play_pause);
        seekBar = (SeekBar) view.findViewById(R.id.play_progress);
        btnPlayType = (ImageButton) view.findViewById(R.id.btn_play_type);

        btnPlayState.setOnClickListener(this);
        view.findViewById(R.id.btn_play_next).setOnClickListener(this);
        view.findViewById(R.id.btn_play_previous).setOnClickListener(this);
        btnPlayType.setOnClickListener(this);
        changePlayOrPauseView(false);
        initPlayProgressBar();
    }

    public void setPlayList(List<File> files) {
        this.playList = files;
        currentPlayPosition = 0;
    }

    /**
     * 初始化 播放进度条，以及 播放时间
     */
    private void initPlayProgressBar() {
        tvPlayTime.setText(R.string.default_play_time);
        tvMusicTime.setText(R.string.default_play_time);
        seekBar.setProgress(0);
    }

    /**
     * 修改播放、暂停对应的资源图片
     */
    private void changePlayOrPauseView(boolean isPlaying) {
        if (isPlaying) {
            btnPlayState.setImageResource(R.drawable.bt_play);
        } else {
            btnPlayState.setImageResource(R.drawable.bt_pause);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (CHANGE_PLAY_PROGRESS == msg.what) {
                seekBar.setProgress(curPlayProgress * 100 / mediaPlayer.getDuration());
                tvPlayTime.setText(formatTime(curPlayProgress));
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_lsit:
                break;
            case R.id.btn_play_type:
                loopPlayType();
                break;
            case R.id.btn_play_pause:
                if (isPlaying()) {
                    stop();
                } else {
                    play();
                }
                break;
            case R.id.btn_play_previous:
                reset();
                initPlayProgressBar();
                playPrevious();
                break;
            case R.id.btn_play_next:
                reset();
                initPlayProgressBar();
                playNext();
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isFinish = true;
        initPlayProgressBar();
        changePlayOrPauseView(false);
        if (curPlayType == PlayType.LOOP_SINGLE)
            startPlay(currentPlayPosition);
        else
            playNext();
    }

    /**
     * 播放
     */
    public void startPlay(int position) {
        try {
            File file = playList.get(position);
            String path = file != null ? file.getPath() : "";
            if (!TextUtils.isEmpty(path)) {
                isFinish = false;
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                Thread.sleep(1000);
                mediaPlayer.start();
                progressThread.start();
                changePlayOrPauseView(true);
                tvMusicTime.setText(formatTime(mediaPlayer.getDuration()));
                currentPlayPosition = position;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    private void play() {
        mediaPlayer.start();
        changePlayOrPauseView(true);
    }

    private void stop() {
        mediaPlayer.pause();
        changePlayOrPauseView(false);
    }

    private void reset() {
        mediaPlayer.reset();
        isFinish = true;
        changePlayOrPauseView(false);
    }

    /**
     * 下一曲
     */
    private void playNext() {
        int position = currentPlayPosition + 1;
        if (curPlayType == PlayType.ORDER_LIST) {
            if (position >= playList.size()) {
                return;
            } else {
                startPlay(position);
            }
        } else if (curPlayType == PlayType.LOOP_LIST || curPlayType == PlayType.LOOP_SINGLE) {
            position = position >= playList.size() ? 0 : position;
            startPlay(position);
        } else if (curPlayType == PlayType.RANDOM) {
            startPlay(randomPosition());
        }
    }

    /**
     * 上一曲
     */
    private void playPrevious() {
        int position = currentPlayPosition - 1;
        if (curPlayType == PlayType.ORDER_LIST) {
            if (position <= 0) {
                return;
            } else {
                startPlay(position);
            }
        } else if (curPlayType == PlayType.LOOP_LIST || curPlayType == PlayType.LOOP_SINGLE) {
            position = position <= 0 ? playList.size() - 1 : position;
            startPlay(position);
        } else if (curPlayType == PlayType.RANDOM) {
            startPlay(randomPosition());
        }
    }

    private int randomPosition() {
        int value = (int) (Math.random() * playList.size());
        Log.i("musicControl", "--->random play new position:" + value);
        return (int) (Math.random() * playList.size());
    }

    /**
     * 播放方式 显示的资源
     */
    private void changePlayTypeView() {
        switch (curPlayType) {
            case ORDER_LIST:
                btnPlayType.setImageResource(R.drawable.bt_play_order_type);
                break;
            case LOOP_LIST:
                btnPlayType.setImageResource(R.drawable.bt_play_loop_type);
                break;
            case RANDOM:
                btnPlayType.setImageResource(R.drawable.bt_play_random_type);
                break;
            case LOOP_SINGLE:
                btnPlayType.setImageResource(R.drawable.bt_play_roundsingl);
                break;
        }
    }

    /**
     * 修改播放方式
     */
    private void loopPlayType() {
        int msgId = 0;
        switch (curPlayType) {
            case ORDER_LIST:
                curPlayType = PlayType.LOOP_LIST;
                msgId = R.string.play_loop_list;
                break;
            case LOOP_LIST:
                curPlayType = PlayType.RANDOM;
                msgId = R.string.play_random_list;
                break;
            case RANDOM:
                curPlayType = PlayType.LOOP_SINGLE;
                msgId = R.string.play_loop_single;
                break;
            case LOOP_SINGLE:
                curPlayType = PlayType.ORDER_LIST;
                msgId = R.string.play_order_list;
                break;
        }
        if (msgId != 0) {
            Toast.makeText(context, context.getResources().getString(msgId), Toast.LENGTH_SHORT).show();
        }
        changePlayTypeView();
    }

    private String formatTime(int duration) {
        StringBuilder buffer = new StringBuilder();
        int m = duration / 1000 / 60;
        buffer.append(m >= 10 ? m : "0" + m).append(":");
        int s = duration / 1000 % 60;
        buffer.append(s >= 10 ? s : "0" + s);
        return TextUtils.isEmpty(buffer.toString()) ? "00:00" : buffer.toString();
    }

    private boolean isFinish = false;
    Thread progressThread = new Thread() {
        @Override
        public void run() {
            while (!isFinish) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (mediaPlayer != null) {
                    curPlayProgress = mediaPlayer.getCurrentPosition();
                    mHandler.sendEmptyMessage(CHANGE_PLAY_PROGRESS);
                }
            }
        }
    };
}
