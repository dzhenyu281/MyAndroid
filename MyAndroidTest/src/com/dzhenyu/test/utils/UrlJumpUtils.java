package com.dzhenyu.test.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.dzhenyu.test.CommonWebActivity;
import com.dzhenyu.test.R;

import static android.widget.Toast.*;

/**
 * Created by onlymem on 2015/10/20.
 */
public class UrlJumpUtils {
    /**
     * webview 打开
     */
    public final static int OPEN_URL_WITH_WEBVIEW = 0;
    /**
     * browser 打开
     */
    public final static int OPEN_URL_WITH_BROWSER = 1;


    private static String URL_START_WITH = "http://";


    public static void openUrlWithBrowser(Context context, String url) {
        openUrl(context, url, OPEN_URL_WITH_BROWSER, 0);
    }

    public static void openUrlWithWebView(Context context, String url, int urlName) {
        openUrl(context, url, OPEN_URL_WITH_WEBVIEW, urlName);
    }

    private static void openUrl(Context context, String url, int urlFlag, int urlName) {

        if (TextUtils.isEmpty(url) || !url.startsWith(URL_START_WITH))
            return;
        switch (urlFlag) {
            case OPEN_URL_WITH_WEBVIEW:
//                ActivityDispatcher dispatcher = new ActivityDispatcher();
//                dispatcher.toCommonWebView(context, url, urlName);
                Intent intent = new Intent(context, CommonWebActivity.class);
                intent.putExtra(CommonWebActivity.EXTRA_URL, url);
                intent.putExtra(CommonWebActivity.EXTRA_URL_TITLE, urlName);
                context.startActivity(intent);
                break;
            case OPEN_URL_WITH_BROWSER:
                Uri uri = Uri.parse(url);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(it);
                } catch (Exception e) {
                    makeText(context, R.string.browser_cannot_find, LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
