package com.dzhenyu.test;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.dzhenyu.test.ui.CustomVideo;


/**
 * Created by onlymem on 2015/9/9.
 */
public class VideoPlayFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vdieo_play_layout, null);
        CustomVideo videoView = (CustomVideo) view.findViewById(R.id.videoView_main);
        final Uri uri = Uri.parse(getMP4());
        videoView.setOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(mContext));
        videoView.start();
        videoView.requestFocus();
        return view;
    }

    private String getMP4() {
        return "android.resource://" + mContext.getPackageName() + "/" + R.raw.default10;
    }


}
