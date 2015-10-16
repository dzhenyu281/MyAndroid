package com.dzhenyu.test.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by onlymem on 2015/9/22.
 */
public class ScreenOnReceiver extends BroadcastReceiver {
    private static final String TAG=ScreenOnReceiver.class.getSimpleName();
    private static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            Log.i(TAG,"----->has sreen on Broadcast " + count++);
        }
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            Log.i(TAG, "---->has sreen off Broadcast " + count++);
        }
    }
}
