package com.dzhenyu.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzhenyu.test.R;
import com.dzhenyu.test.utils.UrlJumpUtils;

/**
 * Created by onlymem on 2015/10/20.
 */
public class OpenUrlFragment extends BaseFragment implements View.OnClickListener {

    private static String OPEN_URL = "http://www.baidu.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.open_url_layout, container, false);
        ((TextView) view.findViewById(R.id.tv_open_url)).setText(mContext.getString(R.string.open_url, OPEN_URL));
        view.findViewById(R.id.btn_browser_open).setOnClickListener(this);
        view.findViewById(R.id.btn_webview_open).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_browser_open:
                UrlJumpUtils.openUrlWithBrowser(mContext, OPEN_URL);
                break;
            case R.id.btn_webview_open:
                UrlJumpUtils.openUrlWithWebView(mContext, OPEN_URL, 0);
                break;
        }
    }
}
