package com.dzhenyu.test.function.eventbus;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dzhenyu.test.R;
import com.dzhenyu.test.fragment.BaseFragment;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by onlymem on 2015/11/21.
 */
public class EventBusSecondFragment extends BaseFragment {

    private static final String TAG = EventBusSecondFragment.class.getSimpleName();

    private TextView thisViewMessage;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.event_bus_second, container, false);
        thisViewMessage = (TextView) view.findViewById(R.id.tv_event_msg);

        view.findViewById(R.id.btn_send_eventbus_msg).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new StringEvent("这是利用Eventbus发送的一条消息"));
                        thisViewMessage.setText("EventBus消息已发送");
                    }
                });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(StringEvent event) {
        Log.i("TAG", "--------->" + event.getMessage());
        showSnackbar(event.getMessage());
    }


    private void showToash(){

    }

    private void showSnackbar(String msg){
        Snackbar bar=Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
        bar.show();

    }
}
