package com.dzhenyu.test.function.eventbus;

import android.content.Intent;
import android.os.Bundle;
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
public class EventBusFristFragment extends BaseFragment {

    private TextView secondViewMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_bus_frist, container, false);
        secondViewMessage = (TextView) view.findViewById(R.id.tv_event_msg);

        view.findViewById(R.id.btn_start_second_activity).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EventBusSecondActivity.class);
                        startActivity(intent);
//                        getActivity().finish();  Eventbus这里不能finish，。。
                    }
                });

        return view;
    }

    @Subscribe
    public void onEvent(StringEvent event){
        secondViewMessage.setText(event.getMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
