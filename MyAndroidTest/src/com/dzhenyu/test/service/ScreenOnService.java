package com.dzhenyu.test.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.dzhenyu.test.broadcastreceiver.ScreenOnReceiver;

/**
 * Created by onlymem on 2015/9/22.
 */
public class ScreenOnService extends Service {

    private ScreenOnReceiver receiver;
    private static int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new ScreenOnReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
        Log.i("--->","---> on Create service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {

        }

        Intent intent=new Intent(this,ScreenOnService.class);
        startService(intent);
    }
}
