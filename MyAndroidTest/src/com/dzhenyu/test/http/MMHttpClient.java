package com.dzhenyu.test.http;

import android.content.Context;
import android.os.Looper;

import com.dzhenyu.test.http.request.MMRequest;
import com.dzhenyu.test.http.request.MMRequestHeader;
import com.dzhenyu.test.http.response.MMResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;


import cz.msebera.android.httpclient.client.entity.EntityBuilder;


/**
 * Created by onlymem on 2015/11/2.
 */
public class MMHttpClient {
    /**
     * 异步
     */
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    /**
     * 同步
     */
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    private static final String HTTP_SERVIER_URL = "";

    private static final int CONNECT_TIME_OUT = 5000;
    private static final int CONNECT_RETRY_COUNT = 3;

    private static final String REQUET_CONTENT_TYPE = RequestParams.APPLICATION_JSON;

    private String getAbsoluteUrl(String url) {
        return HTTP_SERVIER_URL + url;
    }


    public <T extends MMResponse> void sendPostRequest(Context context,
                                                       MMRequest params,
                                                       String url,
                                                       MMHttpResponseHandler<T> httpResponseHandler) {

        AsyncHttpClient client = isMainLooper() ? syncHttpClient : asyncHttpClient;
        try {
            getCommonRequest(params);
            EntityBuilder builder= EntityBuilder.create().setContentEncoding(new Gson().toJson(params));
            client.setMaxRetriesAndTimeout(CONNECT_RETRY_COUNT, CONNECT_TIME_OUT);
            client.post(context, getAbsoluteUrl(url), builder.build(), REQUET_CONTENT_TYPE, httpResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUplodFile() {

    }

    public void getCommonRequest(MMRequest params) {
        params.header = new MMRequestHeader();
        params.header.requesttime = System.currentTimeMillis();
    }

    protected boolean isMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
