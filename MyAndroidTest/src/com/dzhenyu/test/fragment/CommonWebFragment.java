package com.dzhenyu.test.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dzhenyu.test.CommonWebActivity;
import com.dzhenyu.test.R;

/**
 * Created by onlymem on 2015/10/20.
 */
public class CommonWebFragment extends BaseFragment {


    private WebView mWebView;

    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getActivity().getIntent().getStringExtra(CommonWebActivity.EXTRA_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_web_layout, container, false);
        mWebView = (WebView) view.findViewById(R.id.wv_common_webview);
        initWebView();
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
        return view;
    }

    private void initWebView() {
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        // TODO 为html5提供接口，以便交互
        mWebView.addJavascriptInterface(new JSKit(getActivity()), "appInterface");

        // TODO 为html5提供数据库操作
        String path = mContext.getApplicationContext().getDatabasePath("AppDateBaseName")
                .getAbsolutePath();
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDatabasePath(path);


        mWebView.getSettings().setGeolocationEnabled(true);
        // TODO 设置定位数据库的位置
        // 路径？？？？
        mWebView.getSettings().setGeolocationDatabasePath("path");


    }


    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i("common web view", "----> common web view progress: " + newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.i("common web view", "----> common web view title: " + title);
        }

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i("common web view", "----> common web view finish");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("common web view", "----> common web view started");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            Log.i("common web view",
                  "----> common web view ReceivedError， errorCode：" + errorCode + ";description: "
                  + description + "; failingUrl" +
                  failingUrl);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.i("common web view", "----> common web view LoadResource");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                getActivity().finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class JSKit {
        public Activity activity;

        JSKit(Activity activity) {
            this.activity = activity;
        }

        // @JavascriptInterface 标记这是个接口
        @JavascriptInterface
        public void setWebViewTitle(String title) {
            // 修改布局的title
        }

        @JavascriptInterface
        public float getHight(){
            return 0;
        }
    }
}
