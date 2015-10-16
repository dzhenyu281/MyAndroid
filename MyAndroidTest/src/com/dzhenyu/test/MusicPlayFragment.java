package com.dzhenyu.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dzhenyu.test.adapter.TextAdapter;
import com.dzhenyu.test.ui.MusicPlayControl;
import com.dzhenyu.test.utils.FileSearchUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/9/12.
 */
public class MusicPlayFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView mList;
    private MusicPlayControl musicPlayControl;
    private List<File> files = new ArrayList<>();

    public final static int FINISH_LOADING_DATA = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                files = FileSearchUtils.searchFilesByExtension(mContext, ".mp3");
                handler.sendEmptyMessage(FINISH_LOADING_DATA);
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_play_layout, null);
        mList = (ListView) view.findViewById(R.id.music_list);
        musicPlayControl = (MusicPlayControl) view.findViewById(R.id.play_control);
        mList.setOnItemClickListener(this);
        return view;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FINISH_LOADING_DATA) {
                musicPlayControl.setPlayList(files);
                List<String> fileNames = new ArrayList<>();
                for (File file : files) {
                    fileNames.add(file.getName());
                }
                mList.setAdapter(new TextAdapter(mContext, fileNames, TextAdapter.HEIGHT_TYPE_DEFAULT));
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        musicPlayControl.startPlay(position);
    }
}
