package com.dzhenyu.test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dzhenyu.test.broadcastreceiver.TimeTaskReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by onlymem on 2015/9/17.
 */
public class TimeTaskFragment extends BaseFragment {

    private TimeTaskReceiver timeTaskReceiver;
    private Timer timer;

    private AlarmManager alarmManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TimeTaskReceiver.TIME_TASK_ACTION);
        timeTaskReceiver = new TimeTaskReceiver();
        mContext.registerReceiver(timeTaskReceiver, filter);
        //����һ��ʼ��
        timer = new Timer();
        // ��������ʼ��
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_task_layout, null);
        view.findViewById(R.id.btn_create_timetask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.schedule(task, 1000, 1000);
            }
        });
        view.findViewById(R.id.btn_create_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (timeTaskReceiver != null) {
            timer.cancel(); //��������

            mContext.unregisterReceiver(timeTaskReceiver);
        }
    }


    /**
     * ����һ�� ͨ��TimeTask ��ɶ�ʱ����
     */
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setAction(TimeTaskReceiver.TIME_TASK_ACTION);
            mContext.sendBroadcast(intent);
        }
    };

    /**
     * ��������ϵͳ���� ��ɶ�ʱ����
     * alarmManager.setRepeating();
     * alarmManager.cancel
     */
    private void setAlarm() {
        Intent intent = new Intent();
        intent.setAction(TimeTaskReceiver.TIME_TASK_ACTION);
        intent.putExtra(TimeTaskReceiver.TIME_TASK_ACTION_TIME, format.format(new Date(System.currentTimeMillis())));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        Date date = new Date(System.currentTimeMillis() + 10 * 1000);
        Log.i("------>", "----send time---->" + format.format(date));
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private void createAlarmClock() {
    }
}
