package com.dzhenyu.test.function.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;
import com.dzhenyu.test.ui.SearchFlyView;

/**
 * Created by onlymem on 2015/11/18.
 */
public class SearchFlyFragment extends BaseFragment implements View.OnClickListener {

    private SearchFlyView searchFlyView;
    private static String[] SHOW_SIGNS = new String[]{"QQ", "BaseAnimation", "APK",
            "GFW", "铅笔",//
            "短信", "桌面精灵", "MacBook Pro", "平板电脑", "雅诗兰黛",//
            "Base", "笔记本", "SPY Mouse", "Thinkpad E40", "捕鱼达人",//
            "内存清理", "地图", "导航", "闹钟", "主题",//
            "通讯录", "播放器", "CSDN leak", "安全", "Animation",//
            "美女", "天气", "4743G", "戴尔", "联想",//
            "欧朋", "浏览器", "愤怒的小鸟", "mmShow", "网易公开课",//
            "iciba", "油水关系", "网游App", "互联网", "365日历",//
            "脸部识别", "Chrome", "Safari", "中国版Siri", "苹果",//
            "iPhone5S", "摩托 ME525", "魅族 MX3", "小米"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fly_layout, container,false);
        searchFlyView = (SearchFlyView) view.findViewById(R.id.search_fly_view);
        searchFlyView.setShowType(SearchFlyView.TYPE_SHOW_OUT);
        searchFlyView.setShowSigns(SHOW_SIGNS);
        view.findViewById(R.id.btn_show).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        searchFlyView.show();
    }
}
