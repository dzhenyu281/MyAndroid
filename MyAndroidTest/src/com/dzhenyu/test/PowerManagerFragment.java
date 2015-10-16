package com.dzhenyu.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dzhenyu.test.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/9/18.
 * <p/>
 * --------------BatteryManager 电池电量信息---------------
 * “status”（int类型）…状态，定义值是BatteryManager.BATTERY_STATUS_XXX。
 * “health”（int类型）…健康，定义值是BatteryManager.BATTERY_HEALTH_XXX。
 * “present”（boolean类型）
 * “level”（int类型）…电池剩余容量
 * “scale”（int类型）…电池最大值。通常为100。
 * “icon-small”（int类型）…图标ID。
 * “plugged”（int类型）…连接的电源插座，定义值是BatteryManager.BATTERY_PLUGGED_XXX。
 * “voltage”（int类型）…mV。
 * “temperature”（int类型）…温度，0.1度单位。例如 表示197的时候，意思为19.7度。
 * “technology”（String类型）…电池类型，例如，Li-ion等等。
 * <p/>
 * <p/>
 *
 */
public class PowerManagerFragment extends BaseFragment {

    private BatteryManager batteryManager;
    private PowerChangeBroadcast broadcast;

    protected static int count = 0;
    private List<String> messageList = new ArrayList<>();
    private static int HANDLER_MESSAGE_FLAG = 1;

    private BaseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        batteryManager = (BatteryManager) mContext.getSystemService(Context.BATTERY_SERVICE);
        broadcast = new PowerChangeBroadcast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.power_manager_layout, null);
        adapter = new TextAdapter(mContext, messageList, TextAdapter.HEIGHT_TYPE_WRAPCONTENT);
        ListView list = (ListView) view.findViewById(R.id.battery_change_mesg_list);
        list.setAdapter(adapter);
        return view;
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANDLER_MESSAGE_FLAG) {
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(broadcast, filter);
    }


    class PowerChangeBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                String msg = mContext.getString(R.string.battery_message, count++, intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1),
                        intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) + "", getBatteryStatus(intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1))
                        , getBatteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)), getBatteryHealth(intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)),
                        intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1));
                messageList.add(msg);
                mhandler.sendEmptyMessage(HANDLER_MESSAGE_FLAG);
            }

        }

        private String getBatteryStatus(int status) {
            String statusName = "UNKOWN";
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusName = "BATTERY_STATUS_CHARGING";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusName = "BATTERY_STATUS_DISCHARGING";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    statusName = "BATTERY_STATUS_FULL";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    statusName = "BATTERY_STATUS_NOT_CHARGING";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusName = "BATTERY_STATUS_UNKNOWN";
                    break;
            }
            statusName += " " + status;
            return statusName;
        }

        private String getBatteryPlugged(int plugged) {
            String pluggedName = "UNKOWN";
            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedName = "BATTERY_PLUGGED_AC";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedName = "BATTERY_PLUGGED_USB";
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    pluggedName = "BATTERY_PLUGGED_WIRELESS";
                    break;
            }
            pluggedName += " " + plugged;
            return pluggedName;
        }

        private String getBatteryHealth(int health) {
            String healthName = "UNKOWN";
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    healthName = "BATTERY_HEALTH_UNKNOWN";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthName = "BATTERY_HEALTH_GOOD";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthName = "BATTERY_HEALTH_DEAD";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthName = "BATTERY_HEALTH_OVERHEAT";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthName = "BATTERY_HEALTH_OVER_VOLTAGE";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthName = "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthName = "BATTERY_HEALTH_COLD";
                    break;
            }
            healthName += " " + health;
            return healthName;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (broadcast != null) {
            mContext.unregisterReceiver(broadcast);
            count = 0;
        }
    }
}
