package com.dzhenyu.test.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by onlymem on 2015/9/17.
 */
public class TimeTaskReceiver extends BroadcastReceiver {

    public static final String TIME_TASK_ACTION="time_task_action";
    public static final String TIME_TASK_ACTION_TIME="time_task_action_time";

    private static int count=0;

    @Override
    public void onReceive(Context context, Intent intent) {
         if(intent==null)
             return;
         if(TIME_TASK_ACTION.equals(intent.getAction())){
             intent.getExtras().getString(TIME_TASK_ACTION_TIME);
             Log.i("time task test","---> has time task broadcast: "+count+++" time"+intent.getExtras().getString(TIME_TASK_ACTION_TIME));
         }
    }
}
