package com.dzhenyu.test.http;

import android.content.Context;
import android.util.Log;

import com.dzhenyu.test.http.response.MMResponse;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;


/**
 * Created by onlymem on 2015/10/31.
 */
public abstract class MMHttpResponseHandler<T extends MMResponse> extends TextHttpResponseHandler {

    /**
     * json解析失败
     */
    public static final int HTTP_ERROR_CODE_GSON = 1;
    /**
     * 请求失败
     */
    public static final int HTTP_ERROR_CODE_REQUEST = 0;
    /**
     * 其他
     */
    public static final int HTTP_ERROR_CODE_OTHER = 9;


    private static final String TAG = "httpResonse";

    private Class<T> mclass;
    private Context mcontext;

    public MMHttpResponseHandler(Class<T> claass, Context context) {
        this.mclass = claass;
        mcontext = context;
    }

    @Override
    public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String s,
                          Throwable throwable) {
        Log.i(TAG, getRequestErrorLog(throwable.toString()));
        onFailure(HTTP_ERROR_CODE_REQUEST, null);
    }

    @Override
    public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, String responseString) {
        T object;
        try {
            Gson gson = new Gson();
            object = gson.fromJson(responseString, mclass);
            Log.i(TAG, getRequestSuccessLog(responseString));
            onSuccess(object);
        } catch (Exception e) {
            onFailure(HTTP_ERROR_CODE_GSON, null);
            Log.i(TAG, getRequestErrorLog(e.toString()));
        }
    }


    public String getRequestErrorLog(String message) {
        return "request url:" + getRequestURI() + " fail ,error message: " + message;
    }

    private String getRequestSuccessLog(String responseString) {
        return "request url:" + getRequestURI() + " fail ,response message: " + responseString;
    }


    public abstract void onSuccess(T response);

    public abstract void onFailure(int errorCode, String message);
}
